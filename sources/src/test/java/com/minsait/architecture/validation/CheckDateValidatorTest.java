package com.minsait.architecture.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import javax.validation.ConstraintValidatorContext;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CheckDateValidatorTest {

	@InjectMocks
	private CheckDateValidator checkDateValidator;

	@Mock
	private ConstraintValidatorContext context;

	@Mock
	private CheckDateFormat checkDateFormat;

	@CheckDateFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss.SSS'Z'")
	String date;

	@Test
	public void shouldReturnDate() {
		date = "2019-04-05T08:41:44.786Z";
		when(checkDateFormat.pattern()).thenReturn("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
		checkDateValidator.initialize(checkDateFormat);
		Boolean isValid = checkDateValidator.isValid(date, context);

		assertTrue(isValid);

	}

	@Test
	public void shouldReturnFalse() {
		date = "2sd 44.786Z";
		when(checkDateFormat.pattern()).thenReturn("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
		checkDateValidator.initialize(checkDateFormat);
		Boolean isValid = checkDateValidator.isValid(date, context);

		assertFalse(isValid);

	}

	@Test
	public void shouldReturnTrueNull() {
		date = null;
		when(checkDateFormat.pattern()).thenReturn("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
		checkDateValidator.initialize(checkDateFormat);
		Boolean isValid = checkDateValidator.isValid(date, context);

		assertTrue(isValid);

	}
}
