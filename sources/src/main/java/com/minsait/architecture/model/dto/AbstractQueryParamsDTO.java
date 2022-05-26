package com.minsait.architecture.model.dto;

import java.util.List;

import lombok.Data;

@Data
public abstract class AbstractQueryParamsDTO {

	private Integer paginationKey = 1;
	private Integer pageSize;

	private String ontologyName;
	private List<String> sort;

}
