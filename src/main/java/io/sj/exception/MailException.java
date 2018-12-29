package io.sj.exception;

public class MailException extends Exception{

	private static final long serialVersionUID = 1L;
	private String errorMessage;
	
	public static MailException createErrorMessage(String msg) {
		MailException err = new MailException();
		err.errorMessage = msg;
		return err;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
}



















