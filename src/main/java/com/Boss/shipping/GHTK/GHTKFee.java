package com.Boss.shipping.GHTK;

public class GHTKFee {
	public String name;
	public int fee;
	public int insurance_fee;
	public boolean delivery;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public int getInsurance_fee() {
		return insurance_fee;
	}

	public void setInsurance_fee(int insurance_fee) {
		this.insurance_fee = insurance_fee;
	}

	public boolean isDelivery() {
		return delivery;
	}

	public void setDelivery(boolean delivery) {
		this.delivery = delivery;
	}

}
