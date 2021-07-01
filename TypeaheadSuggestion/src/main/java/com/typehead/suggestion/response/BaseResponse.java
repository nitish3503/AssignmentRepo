package com.typehead.suggestion.response;

/**
 * This class will be used for Response
 * @author Nitish Kumar @ Accolite Digital India Pvt Ltd.
 *
 */
public class BaseResponse {
	
	private int responseCode;
	private boolean status;
	private String message;
	private Object data;

	public BaseResponse() {
	}

	/**
	 * @param responseCode
	 * @param status
	 * @param data
	 */
	public BaseResponse(int responseCode, boolean status, String message, Object data) {
		super();
		this.responseCode = responseCode;
		this.status = status;
		this.data = data;
		this.message = message;
	}

	/**
	 * @return the responseCode
	 */
	public int getResponseCode() {
		return responseCode;
	}

	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
