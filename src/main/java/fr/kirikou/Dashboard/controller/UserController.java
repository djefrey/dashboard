package fr.kirikou.Dashboard.controller;

import fr.kirikou.Dashboard.dto.UserDTO;
import fr.kirikou.Dashboard.dto.UserRegisterDTO;
import fr.kirikou.Dashboard.model.User;
import fr.kirikou.Dashboard.service.DashboardUserLogin;
import fr.kirikou.Dashboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("/")
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(User::toDTO).toList();
    }

    @GetMapping("/me")
    public UserDTO getMe() {
        User user = ((DashboardUserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        return user.toDTO();
    }

    @GetMapping("/{id}")
    public UserDTO getUserInfo(@PathVariable("id") String id) {
        Optional<User> user = userService.getUser(Long.parseLong(id));
        return user.map(User::toDTO).orElse(null);
    }

    @PostMapping("/setmailpassword")
    public void setMailPassword(@RequestParam String email, @RequestParam String password,
                                HttpServletResponse response) throws IOException {
        User user = ((DashboardUserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        if (user.getEmail() != null || userService.isEmailUsed(email)) {
            response.setStatus(400);
            return;
        }

        userService.setMailPassword(user, email, password);
        response.sendRedirect("/index.html");
    }

    @PostMapping("/setpassword")
    public void setPassword(@RequestParam String password,
                            HttpServletResponse response) throws IOException {
        User user = ((DashboardUserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        if (user.getEmail() == null) {
            response.setStatus(400);
            return;
        }

        userService.setPassword(user, password);
        response.sendRedirect("/index.html");
    }
}
