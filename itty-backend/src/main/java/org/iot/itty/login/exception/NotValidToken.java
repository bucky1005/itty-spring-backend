package org.iot.itty.login.exception;

public class NotValidToken extends RuntimeException{
	public NotValidToken(String message){
		super(message);
	}
}
