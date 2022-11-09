package fr.kirikou.Dashboard.controller;

import fr.kirikou.Dashboard.dto.UserDTO;
import fr.kirikou.Dashboard.dto.UserRegisterDTO;
import fr.kirikou.Dashboard.model.User;
import fr.kirikou.Dashboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(User::toDTO).toList();
    }

    @GetMapping("/{id}")
    public UserDTO getUserInfo(@PathVariable("id") String id) {
        Optional<User> user = userService.getUser(Long.parseLong(id));
        return user.map(User::toDTO).orElse(null);
    }
}
