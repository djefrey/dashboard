package fr.kirikou.Dashboard.service;

import fr.kirikou.Dashboard.model.User;
import fr.kirikou.Dashboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequestEntityConverter;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class DashboardOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private UserService userService;

    private Converter<OAuth2UserRequest, RequestEntity<?>> requestEntityConverter = new OAuth2UserRequestEntityConverter();
    private RestOperations restOperations = new RestTemplate();
    private static final ParameterizedTypeReference<Map<String, Object>> PARAMETERIZED_RESPONSE_TYPE = new ParameterizedTypeReference<Map<String, Object>>() {};

    public DashboardOAuth2UserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public DashboardOAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        RequestEntity<?> request = this.requestEntityConverter.convert(userRequest);
        ResponseEntity<Map<String, Object>> response = this.restOperations.exchange(request, PARAMETERIZED_RESPONSE_TYPE);
        Map<String, Object> attributes = response.getBody();

        String name = (String) attributes.get("name");
        String email = (String) attributes.get("email");
        Optional<User> user = userService.getUserByEmail(email);

        if (user.isPresent()) {
            System.out.println("Reuse user " + user.get().getId());
            return new DashboardOAuth2User(user.get(), attributes);
        } else {
            try {
                User newUser = userService.createUserFromOAuth(name, email);

                System.out.println("Create new user ! " + newUser.getId());
                return new DashboardOAuth2User(newUser, attributes);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public class DashboardOAuth2User implements OAuth2User
    {
        private User user;
        private Map<String, Object> attributes;

        public DashboardOAuth2User(User user, Map<String, Object> attributes)
        {
            this.user = user;
            this.attributes = attributes;
        }

        public User getUser() {
            return user;
        }

        @Override
        public Map<String, Object> getAttributes() {
            return attributes;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public String getName() {
            return user.getEmail();
        }
    }
}
