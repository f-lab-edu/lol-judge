package edu.flab.notify.util;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiUrlUtil {

	private final static String SCHEME = "https://";
	private final static String ELECTION_API = "election";

	private final ApiUrlProperties apiUrlProperties;

	public String getElectionApiUrl(Long electionId) {
		String host = apiUrlProperties.getHost();

		if (host.endsWith("/")) {
			host = host.substring(0, host.length() - 1);
		}

		return SCHEME + host + ":" + apiUrlProperties.getPort() + "/api/" + ELECTION_API + "/" + electionId;
	}
}
