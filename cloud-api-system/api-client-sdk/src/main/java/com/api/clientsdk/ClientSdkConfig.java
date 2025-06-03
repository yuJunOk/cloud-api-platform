package com.api.clientsdk;

import com.api.clientsdk.client.GeneralClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author pengYuJun
 */
@Configuration
@ConfigurationProperties("sdk.client")
@Data
@ComponentScan
public class ClientSdkConfig {

	private String accessKey;

	private String secretKey;

	@Bean
	public GeneralClient generalClient() {
		return new GeneralClient(accessKey, secretKey);
	}

}