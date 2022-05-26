package com.minsait.architecture.exceptions.handler;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class TestEntity {

	@NotNull
	private String testParam;
	
}
