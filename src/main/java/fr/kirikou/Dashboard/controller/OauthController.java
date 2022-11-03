package fr.kirikou.Dashboard.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import fr.kirikou.Dashboard.service.DashboardUserDetailsService;
import fr.kirikou.Dashboard.service.GoogleOAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/oauth")
public class OauthController {
    @Autowired
    private GoogleOAuthService googleOAuth;


    @GetMapping("/google")
    public RedirectView googleOauth(Authentication authentication, @RequestParam String code) {
        DashboardUserDetailsService.DashboardUserDetails details = (DashboardUserDetailsService.DashboardUserDetails) authentication.getPrincipal();
        googleOAuth.requestAndSaveToken(details.getUser(), code);
        return new RedirectView("/");
    }
}
