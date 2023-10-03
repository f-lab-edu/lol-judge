package edu.flab.member.config;

import lombok.Data;

@Data
public class ServerAddressProperties {
	private String schme;
	private String host;
	private String port;

	public String fullAddress() {
		return schme + "://" + host + ":" + port;
	}
}
