package fr.kirikou.Dashboard;

import fr.kirikou.Dashboard.repository.UserRepository;
import fr.kirikou.Dashboard.service.DashboardOAuth2UserService;
import fr.kirikou.Dashboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
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
                .anyRequest().authenticated()

                .and()
                .formLogin()
                    .loginProcessingUrl("/login.html")
                    .defaultSuccessUrl("/index.html", true)
                    .failureUrl("/login.html?error=true")

                .and()
                    .logout()
                    .logoutUrl("/logout.html")
                    .logoutUrl("/logout")

                .and()
                    .oauth2Login()
                    .defaultSuccessUrl("/loginSuccess")
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

    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        return new DashboardOAuth2UserService(userService);
    }
}
