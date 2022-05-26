package com.minsait.architecture.model.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinksDTO implements Serializable {
	
	private static final long serialVersionUID = 5642908104438677055L;
	
	private String self;
	private String first;
	private String last;
	private String previous;
	private String next;

}
