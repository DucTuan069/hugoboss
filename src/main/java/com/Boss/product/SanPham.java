package com.Boss.product;

import com.Boss.cuaHangSanPham.CuaHangSanPham;
import com.Boss.danhmuc.DanhMuc;
import com.Boss.ncc.NhaCungCap;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="SANPHAM")
public class SanPham {
    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name = "TENSANPHAM")
    private String tenSanPham;

    @Column(name = "GIA")
    private int giaSanPham;

    @Column(name = "GIASALE")
    private int giaSale;

    @Column(name = "MOTA")
    private String moTa;

    @Column(name = "ANH")
    private String anh;

    @ManyToOne
    @JoinColumn(name = "idncc")
    NhaCungCap nhaCungCap;

    @ManyToOne
    @JoinColumn(name = "iddanhmuc")
    DanhMuc danhMuc;

    @OneToMany(mappedBy = "sanPham")
    @JsonIgnore
    public List<CuaHangSanPham> danhSachCuaHangSanPham;

    @JsonProperty("idSanPham")
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

    public int getGiaSanPham() {
        return giaSanPham;
    }

    public void setGiaSanPham(int giaSanPham) {
        this.giaSanPham = giaSanPham;
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

    public String getAnh() {
        return  anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
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

    public List<CuaHangSanPham> getDanhSachCuaHangSanPham() {
        return danhSachCuaHangSanPham;
    }

    public void setDanhSachCuaHangSanPham(List<CuaHangSanPham> danhSachCuaHangSanPham) {
        this.danhSachCuaHangSanPham = danhSachCuaHangSanPham;
    }
}
