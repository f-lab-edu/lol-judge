package edu.flab.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

	@Value("${access.control.allow-origin}")
	private List<String> allowOrigin;

	@Value("${access.control.methods}")
	private List<String> requestMethods;

	@Value("${access.control.allow-credentials}")
	private boolean allowCredentials;

	@Value("${access.control.max-age}")
	private int maxAge;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins(allowOrigin.toArray(new String[0]))
			.allowedMethods(requestMethods.toArray(new String[0]))
			.allowCredentials(allowCredentials)
			.maxAge(maxAge);
	}
}
