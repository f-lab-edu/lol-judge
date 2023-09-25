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
	public RiotHttpApiClient riotHttpApiClient() {
		WebClient webClient = WebClient.builder()
			.baseUrl("https://kr.api.riotgames.com")
			.defaultHeader("X-Riot-Token", "RGAPI-498e7a69-0231-4c6a-9c72-448cb4bedd13")
			.build();
		HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builder(
			WebClientAdapter.forClient(webClient)).build();
		return httpServiceProxyFactory.createClient(RiotHttpApiClient.class);
	}
}
