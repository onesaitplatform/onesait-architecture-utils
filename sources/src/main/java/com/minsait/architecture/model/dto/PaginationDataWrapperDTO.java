package com.minsait.architecture.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
public class PaginationDataWrapperDTO<T> {

	private List<T> data;
	private PaginationDTO pagination;

	/**
	 * Creates a dto structure for pagination
	 * 
	 * @param data
	 *            error data
	 * @param pagination
	 *            error pagination
	 */
	public PaginationDataWrapperDTO(List<T> data, PaginationDTO pagination) {
		this.data = new ArrayList<>(data);
		this.pagination = pagination;
	}

}
