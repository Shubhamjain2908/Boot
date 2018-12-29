package org.sj.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
public class ErrorResponse 
{
	private String error;
	private int status;
	private String url;
	private String message;
	
}
