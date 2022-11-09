package fr.kirikou.Dashboard.security;

import fr.kirikou.Dashboard.repository.UserRepository;
import fr.kirikou.Dashboard.service.DashboardOAuth2UserService;
import fr.kirikou.Dashboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequestEntityConverter;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class DashboardSecurity {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder encoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .antMatchers(HttpMethod.GET, "/*.html", "/static/**", "/*.js", "/*.json", "/*.ico").permitAll()
                .antMatchers(HttpMethod.POST, "/register").permitAll()
                .antMatchers("/api/**").authenticated()
                .anyRequest().denyAll()

                .and()
                    .formLogin()
                    .loginProcessingUrl("/login.html")
                    .defaultSuccessUrl("/index.html", true)
                    .failureUrl("/login.html?error=true")

                .and()
                    .logout()
                    .logoutUrl("/logout.html")

                .and()
                    .oauth2Login()
                    .defaultSuccessUrl("/index.html")
                    .tokenEndpoint()
                        .accessTokenResponseClient(accessTokenRequestClient())
                    .and()
                    .userInfoEndpoint()
                        .userService(oauth2UserService());

        return http.build();
    }
    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder);
        return authProvider;
    }


    private OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenRequestClient() {
        DefaultAuthorizationCodeTokenResponseClient client = new DefaultAuthorizationCodeTokenResponseClient();

        client.setRequestEntityConverter(new OAuth2AuthorizationCodeGrantRequestEntityConverter() {
            @Override
            public RequestEntity<?> convert(OAuth2AuthorizationCodeGrantRequest request) {
                return setUserAgent(super.convert(request));
            }
        });

        return client;
    }

    private OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        return new DashboardOAuth2UserService(userService);
    }

    public static RequestEntity<?> setUserAgent(RequestEntity<?> request) {
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(request.getHeaders());
        headers.set(HttpHeaders.USER_AGENT, "Dashboard by Kirikou Corp.");

        return new RequestEntity<>(request.getBody(), headers, request.getMethod(), request.getUrl());
    }
}
