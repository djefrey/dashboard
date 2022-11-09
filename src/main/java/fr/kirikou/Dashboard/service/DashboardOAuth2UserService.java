package fr.kirikou.Dashboard.service;

import fr.kirikou.Dashboard.DashboardSecurity;
import fr.kirikou.Dashboard.OauthService;
import fr.kirikou.Dashboard.model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequestEntityConverter;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class DashboardOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private UserService userService;

    private Converter<OAuth2UserRequest, RequestEntity<?>> requestEntityConverter;
    private RestOperations restOperations = new RestTemplate();
    private static final ParameterizedTypeReference<Map<String, Object>> PARAMETERIZED_RESPONSE_TYPE = new ParameterizedTypeReference<Map<String, Object>>() {};

    public DashboardOAuth2UserService(UserService userService) {
        this.userService = userService;
        this.requestEntityConverter = new OAuth2UserRequestEntityConverter() {
            @Override
            public RequestEntity<?> convert(OAuth2UserRequest userRequest) {
                return DashboardSecurity.setUserAgent(super.convert(userRequest));
            }
        };
    }

    @Override
    public DashboardOAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        RequestEntity<?> request = this.requestEntityConverter.convert(userRequest);
        ResponseEntity<Map<String, Object>> response = this.restOperations.exchange(request, PARAMETERIZED_RESPONSE_TYPE);
        Map<String, Object> attributes = response.getBody();

        OauthService service = OauthService.getFromRegistrationId(userRequest.getClientRegistration().getRegistrationId());
        String identifier = (String) attributes.get(service.getIdentifier());
        Optional<User> user = userService.getUserByOauthIdentifier(service, identifier);

        if (user.isPresent()) {
            userService.setUserOauthToken(service, user.get(), userRequest.getAccessToken());
            return new DashboardOAuth2User(user.get(), attributes);
        } else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if (auth != null) {
                User authUser = ((DashboardUserLogin) auth.getPrincipal()).getUser();

                userService.setUserOauthIdentifier(service, authUser, identifier);
                userService.setUserOauthToken(service, authUser, userRequest.getAccessToken());
                return new DashboardOAuth2User(authUser, attributes);
            }

            try {
                User newUser = userService.createUserFromOAuth(service, identifier);
                userService.setUserOauthToken(service, newUser, userRequest.getAccessToken());
                return new DashboardOAuth2User(newUser, attributes);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public class DashboardOAuth2User implements OAuth2User, DashboardUserLogin
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
            return user.getName();
        }
    }
}
