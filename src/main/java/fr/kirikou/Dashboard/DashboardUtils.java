package fr.kirikou.Dashboard;

import fr.kirikou.Dashboard.model.User;
import fr.kirikou.Dashboard.service.DashboardOAuth2UserService;
import fr.kirikou.Dashboard.service.DashboardUserDetailsService;
import fr.kirikou.Dashboard.service.DashboardUserLogin;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class DashboardUtils {
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    public static User getActualUser() {
        return ((DashboardUserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
    }
}
