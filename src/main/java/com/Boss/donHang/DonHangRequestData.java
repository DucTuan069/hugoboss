package com.Boss.donHang;

import java.util.List;

import com.Boss.cuaHangSanPham.SoLuongSanPhamRequestData;

public class DonHangRequestData {
    private String payment;
    private List<SoLuongSanPhamRequestData> sanPham;

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public List<SoLuongSanPhamRequestData> getSanPham() {
        return sanPham;
    }

    public void setSanPham(List<SoLuongSanPhamRequestData> sanPham) {
        this.sanPham = sanPham;
    }
}
