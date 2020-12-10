package com.Boss.product;

import com.Boss.cuaHangSanPham.CuaHangSanPham;
import com.Boss.cuaHangSanPham.CuaHangSanPhamResponseData;
import com.Boss.danhmuc.DanhMuc;
import com.Boss.ncc.NhaCungCap;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class SanPhamResponseData {
    private int id;
    private String tenSanPham;
    private int gia;
    private int giaBan;
    private int giamGia;
    private String anh;
    private String moTaNgan;
    private String moTa;
    private List<CuaHangSanPhamResponseData> soLuong;
    private NhaCungCap nhaCungCap;
    private DanhMuc danhMuc;

    public SanPhamResponseData(SanPham sp){
        this.id = sp.getId();
        this.tenSanPham = sp.getTenSanPham();
        this.gia = sp.getGiaSanPham();
        this.giaBan = sp.getGiaSale();
        this.giamGia =  (sp.getGiaSanPham() - sp.getGiaSale())*100/sp.getGiaSanPham();
        this.anh = sp.getAnh();
        this.moTaNgan = sp.getMoTa();
        this.moTa = sp.getMoTa();
        this.nhaCungCap = sp.getNhaCungCap();
        this.danhMuc = sp.getDanhMuc();

        soLuong = new ArrayList<>();
        List<CuaHangSanPham> chsps = sp.getDanhSachCuaHangSanPham();
        for (int i=0;i<chsps.size();i++){
            CuaHangSanPhamResponseData chspd = new CuaHangSanPhamResponseData(sp, chsps.get(i));
            soLuong.add(chspd);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(int giaBan) {
        this.giaBan = giaBan;
    }

    public int getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(int giamGia) {
        this.giamGia = giamGia;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getMoTaNgan() {
        return moTaNgan;
    }

    public void setMoTaNgan(String moTaNgan) {
        this.moTaNgan = moTaNgan;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    @JsonProperty("kho")
    public List<CuaHangSanPhamResponseData> getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(List<CuaHangSanPhamResponseData> soLuong) {
        this.soLuong = soLuong;
    }

    public NhaCungCap getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(NhaCungCap nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public DanhMuc getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(DanhMuc danhMuc) {
        this.danhMuc = danhMuc;
    }
}
