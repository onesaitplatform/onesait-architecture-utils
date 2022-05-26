package com.minsait.architecture.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

public class DateUtilsTest {

	@InjectMocks
	private DateUtils dateUtils;

	@Test
	public void shouldReturnStringWithFormatValid() {
		final Date date = DateUtils.generateDateNow();
		String dateString = DateUtils.convertDateToStringWithFormat(date);

		assertNotNull(dateString);

	}

	@Test
	public void shouldReturnDate() {
		final Date date = DateUtils.generateDateNow();

		assertNotNull(date);
		assertTrue(date instanceof Date);
	}

	@Test
	public void shouldClone() {
		final Date date = DateUtils.generateDateNow();
		Date dateCloned = DateUtils.clone(date);

		assertNotNull(date);
		assertNotNull(dateCloned);
		assertEquals(date, dateCloned);
	}

	@Test
	public void shouldCloneNull() {
		final Date dateCloned = DateUtils.clone(null);

		assertNull(dateCloned);
	}

}
