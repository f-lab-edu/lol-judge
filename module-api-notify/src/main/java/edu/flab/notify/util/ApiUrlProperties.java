package edu.flab.notify.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "api-url")
public class ApiUrlProperties {
	private String host;
	private String port;
}
