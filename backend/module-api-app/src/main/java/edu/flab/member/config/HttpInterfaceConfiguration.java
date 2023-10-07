package edu.flab.member.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import edu.flab.member.api.RiotHttpApiClient;

@Configuration
public class HttpInterfaceConfiguration {

	@Bean
	public RiotHttpApiClient riotHttpApiClient(RiotApiProperties riotApiProperties) {
		WebClient webClient = WebClient.builder()
			.baseUrl(riotApiProperties.host())
			.defaultHeader("X-Riot-Token", riotApiProperties.apiKey())
			.build();
		HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builder(
			WebClientAdapter.forClient(webClient)).build();
		return httpServiceProxyFactory.createClient(RiotHttpApiClient.class);
	}
}
