package com.core.api.enumerator;

public enum ApiResultStatus {
	
	SUCCESS(1),FAILURE(-1);
	
	int code;
	ApiResultStatus(int code){
		this.code = code;
	}
	public int code() {
		return code;
	}
	
	
}
