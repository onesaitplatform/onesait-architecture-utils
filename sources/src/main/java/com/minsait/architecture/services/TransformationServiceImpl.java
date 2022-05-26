package com.minsait.architecture.services;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minsait.architecture.exceptions.OnesaitException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransformationServiceImpl implements TransformationService {

	final ObjectMapper mapper = new ObjectMapper();

	public <T> T getExtrainfoProperty(JsonNode extrainfo, String fieldName, Class<T> toClass) {

		final JsonNode node = extrainfo.get(fieldName);
		if (node == null) {
			return null;
		} else {
			try {
				return mapper.treeToValue(node, toClass);
			} catch (JsonProcessingException e) {
				log.error("Error parsing extrainfo ", e);
				throw new OnesaitException(e.getMessage());

			}
		}
	}

	public String objectToString(Object object) {
		String result = null;
		try {
			result = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			log.error("Error parsing extrainfo ", e);
			throw new OnesaitException(e.getMessage());
		}
		return result;
	}
}
