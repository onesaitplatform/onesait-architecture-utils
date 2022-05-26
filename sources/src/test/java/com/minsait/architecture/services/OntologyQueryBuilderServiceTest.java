package com.minsait.architecture.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.minsait.architecture.model.dto.AbstractQueryParamsDTO;
import com.minsait.architecture.validation.CheckDateFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class QueryParamsDTO extends AbstractQueryParamsDTO {

	@CheckDateFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss.SSS'Z'")
	private String fromCreationDate;
	@CheckDateFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss.SSS'Z'")
	private String toCreationDate;

	private String type;

	private Long id;

	public QueryParamsDTO() {
		super();
		this.setOntologyName("Generic");
	}

}

class QueryBuilderServiceImpl extends AbstractOntologyQueryBuilderImpl<QueryParamsDTO> {

	@Override
	public String getQueryFilters(QueryParamsDTO queryParamsDTO) {
		List<String> filterList = new ArrayList<>();

		filterList.add(this.filterDateField(queryParamsDTO.getOntologyName(), "creationDate", queryParamsDTO.getFromCreationDate(),
				queryParamsDTO.getToCreationDate()));

		filterList.add(this.filterFieldEquality(queryParamsDTO.getOntologyName(), "type", queryParamsDTO.getType()));
		filterList.add(this.filterFieldEquality(queryParamsDTO.getOntologyName(), "id", queryParamsDTO.getId()));

		filterList = filterList.stream().filter(field -> StringUtils.isNotEmpty(field)).collect(Collectors.toList());
		return String.join(COMMA, filterList);
	}

}

public class OntologyQueryBuilderServiceTest {

	private QueryBuilderServiceImpl queryBuilder;

	@BeforeEach
	public void setUp() {
		queryBuilder = new QueryBuilderServiceImpl();
	}

	@Test
	public void shouldInstanciate() throws Exception {

		QueryBuilderServiceImpl builder = new QueryBuilderServiceImpl();
		assertNotNull(builder);

	}

	@Test
	public void shouldGenerateNofilters() throws Exception {
		QueryParamsDTO queryParams = new QueryParamsDTO();
		queryParams.setOntologyName("Generic");
		queryParams.setPageSize(10);
		queryParams.setPaginationKey(1);

		String filters = queryBuilder.getQueryFilters(queryParams);

		assertTrue(StringUtils.isEmpty(filters));

	}

	@Test
	public void shouldGenerateFilters() throws Exception {
		QueryParamsDTO queryParams = new QueryParamsDTO();
		queryParams.setPageSize(10);
		queryParams.setPaginationKey(1);
		queryParams.setOntologyName("Generic");
		queryParams.setFromCreationDate("2019-02-13T15:37:48.255Z");
		queryParams.setToCreationDate("2019-06-13T15:37:48.255Z");
		String filter = queryBuilder.getQueryFilters(queryParams);

		assertEquals("'Generic.creationDate':{$gte: { $date: '2019-02-13T15:37:48.255Z'}, $lte:{ $date:'2019-06-13T15:37:48.255Z'}}", filter);
	}

	@Test
	public void shouldGenerateEmptyFilters() throws Exception {
		QueryParamsDTO queryParams = new QueryParamsDTO();
		queryParams.setPageSize(10);
		queryParams.setPaginationKey(1);
		queryParams.setOntologyName("Generic");

		String filter = queryBuilder.getQueryFilters(queryParams);

		assertTrue(StringUtils.isEmpty(filter));
	}

	@Test
	public void shouldGenerateFiltersFromDate() throws Exception {
		QueryParamsDTO queryParams = new QueryParamsDTO();
		queryParams.setPageSize(10);
		queryParams.setPaginationKey(1);
		queryParams.setOntologyName("Generic");

		String filter = queryBuilder.getQueryFilters(queryParams);

		assertEquals(StringUtils.EMPTY, filter);
	}

	@Test
	public void shouldGenerateFiltersFromDateEmpty() throws Exception {
		QueryParamsDTO queryParams = new QueryParamsDTO();
		queryParams.setPageSize(10);
		queryParams.setPaginationKey(1);
		queryParams.setOntologyName("Generic");
		queryParams.setType("DELAY_IN");
		String filter = queryBuilder.getQueryFilters(queryParams);

		assertEquals("'Generic.type':{$eq:'DELAY_IN'}", filter);
	}

	@Test
	public void shouldGenerateFiltersFromDateNull() throws Exception {
		QueryParamsDTO queryParams = new QueryParamsDTO();
		queryParams.setPageSize(10);
		queryParams.setPaginationKey(1);
		queryParams.setOntologyName("Generic");
		queryParams.setType("DELAY_IN");
		queryParams.setToCreationDate("2019-02-13T15:37:48.255Z");
		String filter = queryBuilder.getQueryFilters(queryParams);

		assertEquals("'Generic.creationDate':{$lte: { $date: '2019-02-13T15:37:48.255Z'}},'Generic.type':{$eq:'DELAY_IN'}", filter);
	}

	@Test
	public void shouldGenerateFiltersToDate() throws Exception {
		QueryParamsDTO queryParams = new QueryParamsDTO();
		queryParams.setPageSize(10);
		queryParams.setPaginationKey(1);
		queryParams.setOntologyName("Generic");
		queryParams.setToCreationDate("2019-02-13T15:37:48.255Z");
		String filter = queryBuilder.getQueryFilters(queryParams);

		assertEquals("'Generic.creationDate':{$lte: { $date: '2019-02-13T15:37:48.255Z'}}", filter);
	}

	@Test
	public void shouldGenerateFiltersToDateEmpty() throws Exception {
		QueryParamsDTO queryParams = new QueryParamsDTO();
		queryParams.setPageSize(10);
		queryParams.setPaginationKey(1);
		queryParams.setOntologyName("Generic");

		String filter = queryBuilder.getQueryFilters(queryParams);

		assertEquals(StringUtils.EMPTY, filter);
	}

	@Test
	public void shouldGenerateFiltersToDateNull() throws Exception {
		QueryParamsDTO queryParams = new QueryParamsDTO();
		queryParams.setPageSize(10);
		queryParams.setPaginationKey(1);
		queryParams.setType("DELAY_IN");
		queryParams.setId(1L);
		queryParams.setFromCreationDate("2019-02-13T15:37:48.255Z");

		String filter = queryBuilder.getQueryFilters(queryParams);
		assertEquals("'Generic.creationDate':{$gte: { $date: '2019-02-13T15:37:48.255Z'}},'Generic.type':{$eq:'DELAY_IN'},'Generic.id':{$eq:1}", filter);
	}

	@Test
	public void shouldGenerateNoSorting() throws Exception {
		QueryParamsDTO queryParams = new QueryParamsDTO();
		queryParams.setPageSize(10);
		queryParams.setPaginationKey(1);
		queryParams.setOntologyName("Generic");

		String sort = queryBuilder.getQuerySort(queryParams);

		assertTrue(StringUtils.isEmpty(sort));
	}

	@Test
	public void shouldGenerateSortingAscending() throws Exception {
		QueryParamsDTO queryParams = new QueryParamsDTO();
		queryParams.setPageSize(10);
		queryParams.setPaginationKey(1);
		queryParams.setSort(Arrays.asList("type"));
		queryParams.setOntologyName("Generic");

		String sort = queryBuilder.getQuerySort(queryParams);

		assertEquals(".sort({'Generic.type': +1})", sort);
	}

	@Test
	public void shouldGenerateSortingAscendingWithPlus() throws Exception {
		QueryParamsDTO queryParams = new QueryParamsDTO();
		queryParams.setPageSize(10);
		queryParams.setPaginationKey(1);
		queryParams.setSort(Arrays.asList("+type"));
		queryParams.setOntologyName("Generic");

		String sort = queryBuilder.getQuerySort(queryParams);

		assertTrue(StringUtils.isNotEmpty(sort));
		assertEquals(".sort({'Generic.type': +1})", sort);
	}

	@Test
	public void shouldGenerateSortingAscendingSkipEmtpy() throws Exception {
		QueryParamsDTO queryParams = new QueryParamsDTO();
		queryParams.setPageSize(10);
		queryParams.setPaginationKey(1);
		queryParams.setSort(Arrays.asList("type", null, ""));
		queryParams.setOntologyName("Generic");

		String sort = queryBuilder.getQuerySort(queryParams);

		assertEquals(".sort({'Generic.type': +1})", sort);
	}

	@Test
	public void shouldGenerateSortingAscendingDescending() throws Exception {
		QueryParamsDTO queryParams = new QueryParamsDTO();
		queryParams.setPageSize(10);
		queryParams.setPaginationKey(1);
		queryParams.setSort(Arrays.asList("type", "-type"));
		queryParams.setOntologyName("Generic");

		String sort = queryBuilder.getQuerySort(queryParams);

		assertTrue(StringUtils.isNotEmpty(sort));
		assertEquals(".sort({'Generic.type': +1,'Generic.type': -1})", sort);
	}

	@Test
	public void shouldGenerateSortingDescending() throws Exception {
		QueryParamsDTO queryParams = new QueryParamsDTO();
		queryParams.setPageSize(10);
		queryParams.setPaginationKey(1);
		queryParams.setSort(Arrays.asList("-type"));
		queryParams.setOntologyName("Generic");

		String sort = queryBuilder.getQuerySort(queryParams);

		assertEquals(".sort({'Generic.type': -1})", sort);
	}

	@Test
	public void shouldNotGenerateSorting() throws Exception {
		QueryParamsDTO queryParams = new QueryParamsDTO();
		queryParams.setPageSize(10);
		queryParams.setPaginationKey(1);
		queryParams.setSort(Arrays.asList("", null, ""));
		queryParams.setOntologyName("Generic");

		String sort = queryBuilder.getQuerySort(queryParams);

		assertTrue(StringUtils.isEmpty(sort));
	}

	@Test
	public void shouldGeneratePagination() throws Exception {
		QueryParamsDTO queryParams = new QueryParamsDTO();
		queryParams.setPageSize(1);
		queryParams.setPaginationKey(2);
		queryParams.setOntologyName("Generic");

		String pagination = queryBuilder.getQueryPagination(queryParams);

		assertEquals(".skip(1).limit(1)", pagination);
	}

	@Test
	public void shouldGenerateDefaultPagination() throws Exception {
		QueryParamsDTO queryParams = new QueryParamsDTO();
		queryParams.setOntologyName("Generic");

		String pagination = queryBuilder.getQueryPagination(queryParams);

		assertTrue(StringUtils.isEmpty(pagination));
	}

	@Test
	public void shouldGenerateQuery() throws Exception {
		QueryParamsDTO queryParams = new QueryParamsDTO();
		queryParams.setPageSize(1);
		queryParams.setPaginationKey(2);
		queryParams.setSort(Arrays.asList("type"));
		queryParams.setOntologyName("Generic");
		queryParams.setType("DELAY_IN");
		String pagination = queryBuilder.generateQuery(queryParams);

		assertEquals("db.Generic.find({'Generic.type':{$eq:'DELAY_IN'}}).sort({'Generic.type': +1}).skip(1).limit(1);", pagination);
	}
}
