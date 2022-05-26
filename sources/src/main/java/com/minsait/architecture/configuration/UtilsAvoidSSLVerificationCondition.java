package com.minsait.architecture.configuration;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;


public class UtilsAvoidSSLVerificationCondition implements Condition{

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		String avoidSSLVerification = context.getEnvironment().getProperty("ms.avoidSSLVerification");
		return "true".equals(avoidSSLVerification);

	}

}
