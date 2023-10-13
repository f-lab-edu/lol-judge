package edu.flab;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimplePBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableEncryptableProperties
public class JasyptConfiguration {

	@Bean("jasyptStringEncryptor")
	public StringEncryptor stringEncryptor() {
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		SimplePBEConfig config = new SimplePBEConfig();
		config.setPassword(System.getProperty("jasypt.encryptor.password"));
		config.setAlgorithm("PBEWithMD5AndDES");
		config.setKeyObtentionIterations("1000");
		config.setPoolSize("1");
		config.setProviderName("SunJCE");
		config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
		config.setIvGeneratorClassName("org.jasypt.iv.NoIvGenerator");
		encryptor.setStringOutputType("base64");
		encryptor.setConfig(config);
		log.info("jasypt password = {}", config.getPassword());
		return encryptor;
	}
}
