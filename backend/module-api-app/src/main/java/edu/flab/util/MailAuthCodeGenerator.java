package edu.flab.util;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class MailAuthCodeGenerator {

	public String generateAuthCode() {
		return UUID.randomUUID().toString();
	}
}
