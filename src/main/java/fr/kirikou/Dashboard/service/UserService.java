package fr.kirikou.Dashboard.service;

import fr.kirikou.Dashboard.OauthService;
import fr.kirikou.Dashboard.dto.UserRegisterDTO;
import fr.kirikou.Dashboard.model.User;
import fr.kirikou.Dashboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
            throw new Exception("name too short - to change !!!!!!!");
        }

        if (data.getPassword().length() < 8) {
            throw new Exception("password too short - to change !!!!!!!");
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

    public User createUserFromOAuth(OauthService service, String identifier) throws Exception {
        if (isOauthIdentifierUsed(service, identifier)) {
            throw new Exception("identifier used - to change !!!!!!!");
        }

        User user = new User();
        user.setName(identifier);
        user.setPassword(null);
        setUserOauthIdentifier(service, user, identifier);

        return userRepository.save(user);
    }

    public boolean isEmailUsed(String email) {
        return userRepository.getUserByEmail(email).isPresent();
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public Optional<User> getUserByOauthIdentifier(OauthService service, String identifier) {
        switch (service) {
            case GOOGLE: return userRepository.getUserByGoogleMail(identifier);
            case REDDIT: return userRepository.getUserByRedditName(identifier);
            default: return Optional.empty();
        }
    }

    public void setUserOauthIdentifier(OauthService service, User user, String identifier) {
        switch (service) {
            case GOOGLE -> user.setGoogleMail(identifier);
            case REDDIT -> user.setRedditName(identifier);
        }
        userRepository.save(user);
    }

    public void setUserOauthToken(OauthService service, User user, OAuth2AccessToken token) {
        switch (service) {
            case GOOGLE: {
                user.setGoogleToken(token.getTokenValue());
                user.setGoogleTokenExpires(Date.from(token.getExpiresAt()));
            }
            case REDDIT: {
                user.setRedditToken(token.getTokenValue());
                user.setRedditTokenExpires(Date.from(token.getExpiresAt()));
            }
        }
        userRepository.save(user);
    }

    public boolean isOauthIdentifierUsed(OauthService service, String identifier) {
        switch (service) {
            case GOOGLE: return userRepository.getUserByGoogleMail(identifier).isPresent();
            case REDDIT: return userRepository.getUserByRedditName(identifier).isPresent();
            default: return false;
        }
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(user -> users.add(user));
        return users;
    }
}
