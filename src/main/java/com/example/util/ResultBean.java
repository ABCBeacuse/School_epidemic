
package com.example.util;


import java.util.List;

public class ResultBean {

	private boolean success;
	private String message;
	private Object data;

	public void setDataList(List<?> dataList) {
		this.dataList = dataList;
	}
	public List<?> getDataList() {
		return dataList;
	}
	private List<?> dataList;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}
	
	public ResultBean(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
	
	/**
	 * @param success
	 * @param data
	 * @param message
	 */
	public ResultBean(boolean success, Object data, String message) {
		this.success = success;
		this.message = message;
		this.data = data;
	}
	
	public ResultBean() {
		super();
	}
	
	@Override
	public String toString() {
		return "Result [success=" + success + ", message=" + message + "]";
	}
	
}
