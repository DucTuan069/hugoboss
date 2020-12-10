package com.Boss.cuaHangSanPham;

public class SoLuongSanPhamRequestData {
    private int idSanPham;
    private int idCuaHang;
    private int soLuong;
    private String shipping;

    public int getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(int idSanPham) {
        this.idSanPham = idSanPham;
    }

    public int getIdCuaHang() {
        return idCuaHang;
    }

    public void setIdCuaHang(int idCuaHang) {
        this.idCuaHang = idCuaHang;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }
}
