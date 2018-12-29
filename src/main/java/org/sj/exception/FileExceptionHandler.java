package org.sj.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class FileExceptionHandler 
{
	@ExceptionHandler({ DataNotFoundException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorResponse handleDataNotFoundException(HttpServletRequest req, DataNotFoundException ex) 
	{
		ErrorResponse response = new ErrorResponse();
		response.setMessage(ex.getMessage());
		response.setError(HttpStatus.NOT_FOUND.name());
		response.setStatus(HttpStatus.NOT_FOUND.value());
		response.setUrl(req.getRequestURI());
		return response;
	}
	
	@ExceptionHandler({ BadRequestException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse handleBadRequestException(HttpServletRequest req, BadRequestException ex) 
	{
		ErrorResponse response = new ErrorResponse();
		response.setMessage(ex.getMessage());
		response.setError(HttpStatus.BAD_REQUEST.name());
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setUrl(req.getRequestURI());
		return response;
	}
	
	
	@ExceptionHandler({ StorageFileNotFoundException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorResponse handleStorageFileNotFoundException(HttpServletRequest req, StorageFileNotFoundException ex) 
	{
		ErrorResponse response = new ErrorResponse();
		response.setMessage(ex.getMessage());
		response.setError(HttpStatus.NOT_FOUND.name());
		response.setStatus(HttpStatus.NOT_FOUND.value());
		response.setUrl(req.getRequestURI());
		return response;
	}
	
	@ExceptionHandler({ FileNotSupportedException.class })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ResponseBody
	public ErrorResponse handleFileNotSupportedException(HttpServletRequest req, FileNotSupportedException ex) 
	{
		ErrorResponse response = new ErrorResponse();
		response.setMessage(ex.getMessage());
		response.setError(HttpStatus.NO_CONTENT.name());
		response.setStatus(HttpStatus.NO_CONTENT.value());
		response.setUrl(req.getRequestURI());
		return response;
	}
}
