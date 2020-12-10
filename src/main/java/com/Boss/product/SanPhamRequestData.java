package com.Boss.product;

import org.springframework.web.multipart.MultipartFile;

public class SanPhamRequestData {
    private int nhaCungCap;
    private int danhMuc;
    private String tenSanPham;
    private int gia;
    private int giaSale;
    private String moTa;
    private MultipartFile anh;

    public int getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(int nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public int getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(int danhMuc) {
        this.danhMuc = danhMuc;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getGiaSale() {
        return giaSale;
    }

    public void setGiaSale(int giaSale) {
        this.giaSale = giaSale;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public MultipartFile getAnh() {
        return anh;
    }

    public void setAnh(MultipartFile anh) {
        this.anh = anh;
    }
}
