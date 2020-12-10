package com.Boss.cuaHangSanPham;

import com.Boss.cuaHangSanPham.CuaHangSanPham;
import com.Boss.product.SanPham;
import com.Boss.shop.CuaHang;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class CuaHangSanPhamResponseData {
    @JsonUnwrapped
    private CuaHang cuaHang;

    private int idSanPham;
    private String tenSanPham;
    private int soLuong;

    public CuaHangSanPhamResponseData(SanPham sp, CuaHangSanPham chsp) {
        this.idSanPham = sp.getId();
        this.tenSanPham = sp.getTenSanPham();
        this.soLuong = chsp.getSoLuong();
        this.cuaHang = chsp.getCuaHang();
    }

    public CuaHang getCuaHang() {
        return cuaHang;
    }

    public void setCuaHang(CuaHang cuaHang) {
        this.cuaHang = cuaHang;
    }

    public int getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(int idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
