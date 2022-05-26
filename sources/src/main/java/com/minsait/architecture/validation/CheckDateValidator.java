package com.minsait.architecture.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CheckDateValidator implements ConstraintValidator<CheckDateFormat, String> {

	private String pattern;

	@Override
	public void initialize(CheckDateFormat constraintAnnotation) {
		this.pattern = constraintAnnotation.pattern();
	}

	@Override
	public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
		Boolean isValid = Boolean.TRUE;
		if (object != null) {
			try {
				new SimpleDateFormat(pattern).parse(object);
			} catch (ParseException e) {
				log.error("Exception parsing date", e);
				isValid = Boolean.FALSE;
			}
		}
		return isValid;
	}
}