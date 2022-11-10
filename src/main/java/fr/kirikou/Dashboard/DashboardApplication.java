package fr.kirikou.Dashboard;

import fr.kirikou.Dashboard.model.User;
import fr.kirikou.Dashboard.service.DashboardUserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@RestController
public class DashboardApplication {
	@Autowired
	private ResourceLoader resourceLoader;

	public static void main(String[] args) {
		SpringApplication.run(DashboardApplication.class, args);
	}

	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;

	@GetMapping(value = "/*.html")
	public ResponseEntity<Resource> sendHtml(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();

		if (!path.equals("/index.html")) {
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/index.html");
			dispatcher.forward(request, response);
			return null;
		} else {
			Resource resource = resourceLoader.getResource("/WEB-INF/web/index.html");
			return ResponseEntity.ok()
					.contentLength(resource.contentLength())
					.contentType(MediaType.TEXT_HTML)
					.body(resource);
		}
	}
}
