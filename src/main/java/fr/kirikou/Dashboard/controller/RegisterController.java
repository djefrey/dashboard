package fr.kirikou.Dashboard.controller;

import fr.kirikou.Dashboard.dto.UserDTO;
import fr.kirikou.Dashboard.dto.UserRegisterDTO;
import fr.kirikou.Dashboard.exceptions.InvalidCredentialsException;
import fr.kirikou.Dashboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class RegisterController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public UserDTO registerNewAccount(@ModelAttribute UserRegisterDTO data, HttpServletResponse response) throws IOException {
        try {
            UserDTO dto = userService.createUser(data).toDTO();
            response.sendRedirect("/index.html");
            return dto;
        } catch (InvalidCredentialsException e) {
            response.setStatus(422);
            response.sendRedirect("/register.html?errorMsg=\"" + e.getMessage() + "\"");
            return null;
        }
    }
}
