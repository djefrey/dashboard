package fr.kirikou.Dashboard;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class DashboardMvc implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/WEB-INF/web/static/");
        registry.addResourceHandler("/*.js").addResourceLocations("/WEB-INF/web/");
        registry.addResourceHandler("/*.json").addResourceLocations("/WEB-INF/web/");
        registry.addResourceHandler("/*.ico").addResourceLocations("/WEB-INF/web/");
        registry.addResourceHandler("/index.html").addResourceLocations("/WEB-INF/web/");
    }
}
