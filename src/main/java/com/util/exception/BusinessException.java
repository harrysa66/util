package com.util.exception;



public class BusinessException extends Exception {
	private static final long serialVersionUID = 886639508328728005L;
	
	//异常代码
	private Integer code;
	
	public BusinessException(String msg) {
		super(msg);
	}
	
	public BusinessException(Integer code, String msg) {
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