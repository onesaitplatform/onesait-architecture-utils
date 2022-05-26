package com.minsait.architecture.model.error;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ResponseErrorList {

	private List<ResponseError> errors;

	/**
	 * Creates a error structure from a error list
	 * 
	 * @param errors
	 *            error errros
	 */
	public ResponseErrorList(List<ResponseError> errors) {
		super();
		this.errors = new ArrayList<>(errors);

	}

	public ResponseErrorList() {
	}

}
