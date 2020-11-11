package com.payload;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtReponse {
	private String tokken;
	private String type = "Bearer";
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTokken() {
		return tokken;
	}

	public void setTokken(String tokken) {
		this.tokken = tokken;
	}

}
