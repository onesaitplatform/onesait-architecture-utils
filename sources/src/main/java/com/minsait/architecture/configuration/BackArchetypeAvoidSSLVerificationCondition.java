package com.minsait.architecture.configuration;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class BackArchetypeAvoidSSLVerificationCondition implements Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		String avoidSSLVerification = context.getEnvironment().getProperty("archetype.avoidsslverification");
		return "true".equals(avoidSSLVerification);

	}

}
