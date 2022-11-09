package fr.kirikou.Dashboard.controller;

import fr.kirikou.Dashboard.dto.UserDTO;
import fr.kirikou.Dashboard.dto.UserRegisterDTO;
import fr.kirikou.Dashboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserDTO registerNewAccount(@RequestBody UserRegisterDTO data) throws Exception {
        return userService.createUser(data).toDTO();
    }
}
