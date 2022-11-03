package fr.kirikou.Dashboard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/oauth")
public class OauthController {
    @GetMapping("/google")
    public RedirectView googleOauth(@RequestParam String code) {
        System.out.println("OAuth Google: " + code);
        return new RedirectView("/");
    }
}
