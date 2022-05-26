package com.minsait.architecture.model.dto.converter;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class GeometryDTO {

	@JsonProperty("type")
	private String type;
	@JsonProperty("coordinates")
	private List<Object> coordinates;

}
