package com.Boss.user;

import com.Boss.donHang.DonHang;
import com.Boss.gioHang.GioHang;
import com.Boss.shop.CuaHang;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="TAIKHOAN")
public class TaiKhoan {
    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name = "TENDANGNHAP")
    private String tenDangNhap;

    @JsonIgnore
    @Column(name = "MATKHAU")
    private String matKhau;

    @Column(name = "HOTEN")
    private String hoTen;

    @Column(name = "GIOITINH")
    private int gioiTinh;

    @Column(name = "SDT")
    private String sdt;

    @Column(name = "DIACHI")
    private String diaChi;

    @Column(name = "LOAITAIKHOAN")
    private int loaiTaiKhoan;

    @ManyToOne
    @JoinColumn(name = "IDCUAHANG")
    private CuaHang cuaHang;

    @OneToMany(mappedBy = "khachHang")
    @JsonIgnore
    @Fetch(FetchMode.JOIN)
    public List<DonHang> danhSachDonHang;

    @OneToMany(mappedBy = "kh")
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<GioHang> gioHang;

    public CuaHang getCuaHang() {
        return cuaHang;
    }

    public void setCuaHang(CuaHang cuaHang) {
        this.cuaHang = cuaHang;
    }

    public int getId() {
        return id;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public GioiTinh getGioiTinh() {
        return GioiTinh.valueOf(this.gioiTinh);
    }

    public void setGioiTinh(int gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public void setGioiTinh(GioiTinh egt) {
        this.gioiTinh = egt.toInt();
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

    public LoaiTaiKhoan getLoaiTaiKhoan() {
        return LoaiTaiKhoan.valueOf(this.loaiTaiKhoan);
    }

    public void setLoaiTaiKhoan(int loaiTaiKhoan) {
        this.loaiTaiKhoan = loaiTaiKhoan;
    }

    public void setLoaiTaiKhoan(LoaiTaiKhoan role) {
        this.loaiTaiKhoan = role.toInt();
    }

    public List<DonHang> getDanhSachDonHang() {
        return danhSachDonHang;
    }

    public void setDanhSachDonHang(List<DonHang> danhSachDonHang) {
        this.danhSachDonHang = danhSachDonHang;
    }

    public void setId(int id) {
        this.id = id;
    }
}
