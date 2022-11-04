package fr.kirikou.Dashboard;

import fr.kirikou.Dashboard.service.DashboardOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@RestController
public class DashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(DashboardApplication.class, args);
	}

	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;

	@GetMapping("/loginSuccess")
	public String getLoginInfo(OAuth2AuthenticationToken token)
	{
		System.out.println(token.getPrincipal());
		for (Map.Entry<String, Object> entry : token.getPrincipal().getAttributes().entrySet())
			System.out.println(entry.getKey() + " = " + entry.getValue());
		System.out.println(((DashboardOAuth2UserService.DashboardOAuth2User) token.getPrincipal()).getUser().getId());
		return "loginSuccess";
	}
}
