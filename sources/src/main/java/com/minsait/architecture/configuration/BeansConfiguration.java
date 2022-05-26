package com.minsait.architecture.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeansConfiguration {

	@Value("${openplatform.token.user}")
	private String userLoginOP;

	@Value("${openplatform.token.password}")
	private String pwdLoginOP;

	@Bean(name = "loginRestTemplate")
	public RestTemplate loginRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userLoginOP, pwdLoginOP));

		return restTemplate;
	}

}
