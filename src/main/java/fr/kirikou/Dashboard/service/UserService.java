package fr.kirikou.Dashboard.service;

import fr.kirikou.Dashboard.dto.UserRegisterDTO;
import fr.kirikou.Dashboard.model.User;
import fr.kirikou.Dashboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public User createUser(UserRegisterDTO data) throws Exception {
        if (data.getUsername().length() < 4) {
            throw new Exception("name too short");
        }

        if (data.getPassword().length() < 8) {
            throw new Exception("password too short");
        }

        if (isEmailUsed(data.getEmail())) {
            throw new Exception("email used - to change !!!!!!!");
        }

        User user = new User();
        user.setName(data.getUsername());
        user.setEmail(data.getEmail());
        user.setPassword(encoder.encode(data.getPassword()));

        return userRepository.save(user);
    }

    public boolean isEmailUsed(String email) {
        return userRepository.getUserByEmail(email) != null;
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(user -> users.add(user));
        return users;
    }
}
