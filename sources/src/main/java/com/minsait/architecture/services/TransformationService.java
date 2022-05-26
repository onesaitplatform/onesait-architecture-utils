package com.minsait.architecture.services;

import com.fasterxml.jackson.databind.JsonNode;

public interface TransformationService {

	public <T> T getExtrainfoProperty(JsonNode extrainfo, String fieldName, Class<T> toClass);

	public String objectToString(Object object);
}
