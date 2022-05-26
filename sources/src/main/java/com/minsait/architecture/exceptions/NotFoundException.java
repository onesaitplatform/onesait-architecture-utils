package com.minsait.architecture.exceptions;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception {

	private static final long serialVersionUID = 4033388050087870714L;
	private final String ontology;
	private final String id;

	public NotFoundException(final String message) {
		super(message);
		this.ontology = StringUtils.EMPTY;
		this.id = StringUtils.EMPTY;
	}

	public NotFoundException(String ontology, String id) {
		super();
		this.ontology = ontology;
		this.id = id;
	}

	public final String getOntology() {
		return ontology;
	}

	public final String getId() {
		return id;
	}

}
