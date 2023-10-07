package edu.flab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import edu.flab.member.config.MailProperties;
import edu.flab.member.config.RiotApiProperties;

@EnableAsync
@EnableRedisHttpSession
@EnableConfigurationProperties({
	MailProperties.class,
	RiotApiProperties.class
})
@SpringBootApplication
public class WebApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}
}
