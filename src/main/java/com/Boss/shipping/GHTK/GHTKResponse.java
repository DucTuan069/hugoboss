package com.Boss.shipping.GHTK;

public class GHTKResponse {
	public String success;
	public String message;
	public GHTKFee fee;

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public GHTKFee getFee() {
		return fee;
	}

	public void setFee(GHTKFee fee) {
		this.fee = fee;
	}

}
