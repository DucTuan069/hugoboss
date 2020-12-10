package com.Boss.shop;

import com.Boss.cuaHangSanPham.CuaHangSanPham;
import com.Boss.user.TaiKhoan;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="CUAHANG")
public class CuaHang {

    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name = "TENCUAHANG", nullable = false)
    private String tenCuaHang;

    @Column(name = "SDT")
    private String sdt;

    @Column(name = "DIACHI")
    private String diaChi;

    @OneToMany(mappedBy = "cuaHang")
    @JsonIgnore
    @Fetch(FetchMode.JOIN)
    public List<TaiKhoan> danhSachNhanVien;

    @OneToMany(mappedBy = "cuaHang")
    @JsonIgnore
    public List<CuaHangSanPham> danhSachCuaHangSanPham;

    @JsonProperty("idCuaHang")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenCuaHang() {
        return tenCuaHang;
    }

    public void setTenCuaHang(String tenCuaHang) {
        this.tenCuaHang = tenCuaHang;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public List<TaiKhoan> getDanhSachNhanVien() {
        return danhSachNhanVien;
    }

    public void setDanhSachNhanVien(List<TaiKhoan> danhSachNhanVien) {
        this.danhSachNhanVien = danhSachNhanVien;
    }
}
