package edu.flab.web.config;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServerAddressProperties {
	private String schme;
	private String host;
	private String port;

	public String fullAddress() {
		return schme + "://" + host + ":" + port;
	}
}
