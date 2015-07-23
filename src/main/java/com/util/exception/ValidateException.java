package com.util.exception;



public class ValidateException extends Exception {
	
	private static final long serialVersionUID = 1L;
	//异常代码
	private Integer code;
	
	public ValidateException(String msg) {
		super(msg);
	}
	
	public ValidateException(Integer code, String msg) {
		super(msg);
		this.code = code;
	}

	public Integer getCode() {
		if(code == null) {
			return 0;
		}
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
}