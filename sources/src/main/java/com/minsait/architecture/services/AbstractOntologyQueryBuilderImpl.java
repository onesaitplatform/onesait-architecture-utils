package com.minsait.architecture.services;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.minsait.architecture.model.dto.AbstractQueryParamsDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractOntologyQueryBuilderImpl<T extends AbstractQueryParamsDTO> implements OntologyQueryBuilder<T> {

	private static final char PARENTHESES_CLOSE = ')';
	private static final String MINUS = "-";
	private static final String PLUS = "+";
	protected static final String COMMA = ",";
	private static final String NATIVE_QUERY_FILTER_TEMPLATE = "'%s.%s':{$eq:%s}";
	private static final String NATIVE_QUERY_GTE_TEMPLATE = "'%s.%s':{$gte: { $date: '%s'}}";
	private static final String NATIVE_QUERY_LTE_TEMPLATE = "'%s.%s':{$lte: { $date: '%s'}}";
	private static final String NATIVE_QUERY_GTE_LTE_TEMPLATE = "'%s.%s':{$gte: { $date: '%s'}, $lte:{ $date:'%s'}}";

	@Override
	public String generateQuery(T queryParamsDTO) {
		final String query = String.format("db.%s.find({%s})%s%s;", queryParamsDTO.getOntologyName(), getQueryFilters(queryParamsDTO),
				getQuerySort(queryParamsDTO), getQueryPagination(queryParamsDTO));
		log.debug("Query to execute: {}", query);
		return query;
	}

	@Override
	public String getQuerySort(T queryParamsDTO) {

		String querySort = StringUtils.EMPTY;
		if (CollectionUtils.isNotEmpty(queryParamsDTO.getSort())) {

			final List<String> sortFieldList = queryParamsDTO.getSort().stream().filter(field -> StringUtils.isNotEmpty(field))
					.map(field -> buildSortField(field, queryParamsDTO.getOntologyName())).collect(Collectors.toList());

			if (CollectionUtils.isNotEmpty(sortFieldList)) {
				log.debug("Sort to apply: {}", String.join(COMMA, sortFieldList));
				querySort = String.format(".sort({%s})", String.join(COMMA, sortFieldList));
			}
		}
		return querySort;
	}

	private String buildSortField(String field, String ontologyName) {
		String order = PLUS;
		StringBuilder fieldName = new StringBuilder();
		if (field.startsWith(MINUS)) {
			order = MINUS;
			fieldName.append(field.substring(NumberUtils.INTEGER_ONE));
		} else if (field.startsWith(PLUS)) {
			fieldName.append(field.substring(NumberUtils.INTEGER_ONE));
		} else {
			fieldName.append(field);
		}
		return String.format("'%s.%s': %s%d", ontologyName, fieldName.toString(), order, 1);
	}

	@Override
	public String getQueryPagination(T queryParamsDTO) {
		StringBuilder builder = new StringBuilder();
		if (queryParamsDTO.getPageSize() != null) {
			builder.append(".skip(");
			builder.append(queryParamsDTO.getPageSize() * (queryParamsDTO.getPaginationKey() - 1));
			builder.append(PARENTHESES_CLOSE);
		}
		if (queryParamsDTO.getPageSize() != null) {
			builder.append(".limit(");
			builder.append(queryParamsDTO.getPageSize());
			builder.append(PARENTHESES_CLOSE);
		}
		return builder.toString();
	}

	public final String filterDateField(String ontologyName, String field, String fromDate, String toDate) {

		String filterDate = StringUtils.EMPTY;
		if (StringUtils.isNotEmpty(fromDate) && StringUtils.isNotEmpty(toDate)) {
			filterDate = String.format(NATIVE_QUERY_GTE_LTE_TEMPLATE, ontologyName, field, fromDate, toDate);
		} else {

			if (StringUtils.isNotEmpty(fromDate)) {
				filterDate = String.format(NATIVE_QUERY_GTE_TEMPLATE, ontologyName, field, fromDate);
			}

			else if (StringUtils.isNotEmpty(toDate)) {
				filterDate = String.format(NATIVE_QUERY_LTE_TEMPLATE, ontologyName, field, toDate);
			}
		}
		return filterDate;
	}

	public final String filterFieldEquality(String ontologyName, String fieldName, String fieldvalue) {
		if (StringUtils.isNotEmpty(fieldvalue)) {
			return String.format(NATIVE_QUERY_FILTER_TEMPLATE, ontologyName, fieldName, String.format("'%s'", fieldvalue));
		} else {
			return StringUtils.EMPTY;
		}
	}

	public final String filterFieldEquality(String ontologyName, String fieldName, Long fieldvalue) {
		if (fieldvalue != null) {
			return String.format(NATIVE_QUERY_FILTER_TEMPLATE, ontologyName, fieldName, String.format("%d", fieldvalue));
		} else {
			return StringUtils.EMPTY;
		}
	}
}
