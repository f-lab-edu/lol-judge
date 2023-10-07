package edu.flab.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerAddressConfiguration {

	@Bean
	@ConfigurationProperties("myserver.backend")
	public ServerAddressProperties backendServer() {
		return new ServerAddressProperties();
	}

	@Bean
	@ConfigurationProperties("myserver.frontend")
	public ServerAddressProperties frontendServer() {
		return new ServerAddressProperties();
	}
}
