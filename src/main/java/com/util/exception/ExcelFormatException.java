package com.util.exception;

public class ExcelFormatException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExcelFormatException(){ 
        super(); 
    } 
    public ExcelFormatException(String msg){ 
        super(msg); 
    } 
    public ExcelFormatException(String msg, Throwable cause){ 
        super(msg, cause); 
    } 
    public ExcelFormatException(Throwable cause){ 
        super(cause); 
    }
}
