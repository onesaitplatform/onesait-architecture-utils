package com.minsait.architecture.services;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minsait.architecture.exceptions.OnesaitException;

@ExtendWith(MockitoExtension.class)
public class TransformationServiceTest {

	@InjectMocks
	private TransformationServiceImpl service;

	@Test
	public void shouldGetExtrainfoProperty() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readTree(" {\"geometry\":{\"type\":\"Point\", \"coordinates\":[1, -6.4]}, \"type\":\"tipo\"}");

		String result = service.getExtrainfoProperty(jsonNode, "type", String.class);

		assertEquals("tipo", result);
	}

	@Test
	public void shouldGetExtrainfoPropertyNull() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readTree(" {\"geometry\":{\"type\":\"Point\", \"coordinates\":[1, -6.4]}, \"type\":\"tipo\"}");

		String result = service.getExtrainfoProperty(jsonNode, "tipo", String.class);

		assertEquals(null, result);
	}

	@Test
	public void shouldGetExtrainfoPropertyException() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readTree(" {\"geometry\":{\"type\":\"Point\", \"coordinates\":[1, -6.4]}, \"type\":\"tipo\"}");

		OnesaitException exception = assertThrows(OnesaitException.class, 
		        () -> service.getExtrainfoProperty(jsonNode, "type", Integer.class));
		
		assertThat(exception).isNotNull();

	}

	@Test
	public void shouldObjectToString() throws IOException {

		Integer sampleObject = new Integer(10);

		String result = service.objectToString(sampleObject);

		assertEquals("10", result);
	}
}
