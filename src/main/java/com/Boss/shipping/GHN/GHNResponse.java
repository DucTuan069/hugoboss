package com.Boss.shipping.GHN;

public class GHNResponse {
    public int code;
    public String message;
    public GHNFee data;
   
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GHNFee getData() {
        return data;
    }

    public void setData(GHNFee data) {
        this.data = data;
    }
}
