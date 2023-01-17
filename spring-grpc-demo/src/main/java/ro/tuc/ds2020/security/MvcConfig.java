package ro.tuc.ds2020.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Class for adding the web app resource path
 * @author Zaharia Tudorita
 */
@EnableWebMvc
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     * Given a registry, it adds the path to all pages of the frontend app
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/static/**")
                .addResourceLocations("/react/build/static/");
        registry.addResourceHandler("/*.js")
                .addResourceLocations("/react/build/");
        registry.addResourceHandler("/*.json")
                .addResourceLocations("/react/build/");
        registry.addResourceHandler("/*.ico")
                .addResourceLocations("/react/build/");
        registry.addResourceHandler("/index.html")
                .addResourceLocations("/react/build/index.html");
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*");
    }
}