package com.minsait.architecture;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import com.minsait.architecture.configuration.SSLUtil;
import com.minsait.architecture.configuration.UtilsAvoidSSLVerificationCondition;

import lombok.extern.slf4j.Slf4j;

@ServletComponentScan
@SpringBootApplication
@Slf4j
public abstract class AbstractOnesaitApplication implements WebMvcConfigurer {

	private static final String VIEW_NAME_FORWARD = "forward:/";
	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/",
			"classpath:/public/" };
	

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS).resourceChain(true).addResolver(new PathResourceResolver());
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/{spring:\\w+}").setViewName(VIEW_NAME_FORWARD);
		registry.addViewController("/**/{spring:\\w+}").setViewName(VIEW_NAME_FORWARD);
		registry.addViewController("/{spring:\\w+}/**{spring:?!(\\.js|\\.css)$}").setViewName(VIEW_NAME_FORWARD);
	}
}
