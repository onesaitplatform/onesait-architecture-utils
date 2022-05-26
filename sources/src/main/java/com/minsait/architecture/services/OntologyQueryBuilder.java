package com.minsait.architecture.services;

import com.minsait.architecture.model.dto.AbstractQueryParamsDTO;

public interface OntologyQueryBuilder<T extends AbstractQueryParamsDTO> {

	String generateQuery(T queryParamsDTO);

	String getQueryFilters(T queryParamsDTO);

	String getQuerySort(T queryParamsDTO);

	String getQueryPagination(T queryParamsDTO);

}
