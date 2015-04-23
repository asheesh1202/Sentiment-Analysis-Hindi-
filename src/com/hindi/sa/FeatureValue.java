package com.hindi.sa;

import com.hindi.ssfreader.Token;

public class FeatureValue {
	Integer id;
	Token token;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Token gettToken() {
		return token;
	}
	public void setToken(Token token) {
		this.token = token;
	}
	
	@Override
	public String toString() {
	
		return "id: "+id+" ,token: "+token;
	}
	
}
