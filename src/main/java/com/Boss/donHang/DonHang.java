package com.Boss.donHang;

import com.Boss.user.TaiKhoan;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "DONHANG")
public class DonHang {
    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name = "TONGTIEN")
    private int tongTien;

    @Column(name = "TRANGTHAI")
    private int trangThaiDonHang;

    @Column(name = "SHIPPINGFEE")
    private int shippingFee;

    @Column(name = "MAVANDON")
    private String maVanDon;

    @Column(name = "SHIPPING")
    private String shipping;

    @ManyToOne
    @JoinColumn(name = "IDTAIKHOAN")
    private TaiKhoan khachHang;

    @OneToMany(mappedBy = "donHang")
    @JsonIgnore
    @Fetch(FetchMode.JOIN)
    private List<DonHangSanPham> dsDonHangSanPham;

    @Column(name = "NGAYTAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayTao;

    @PrePersist
    void ngayTao() {
        this.ngayTao = new Date();
    }

    @Transient
    private String tenCuaHang;

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getTenCuaHang() {
        return tenCuaHang;
    }

    public void setTenCuaHang(String tenCuaHang) {
        this.tenCuaHang = tenCuaHang;
    }

    public List<DonHangSanPham> getDsDonHangSanPham() {
        return dsDonHangSanPham;
    }

    public void setDsDonHangSanPham(List<DonHangSanPham> dsDonHangSanPham) {
        this.dsDonHangSanPham = dsDonHangSanPham;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public TrangThaiDonHang getTrangThaiDonHang() {
        return TrangThaiDonHang.valueOf(this.trangThaiDonHang);
    }

    public void setTrangThaiDonHang(int trangThaiDonHang) {
        this.trangThaiDonHang = trangThaiDonHang;
    }

    public void setTrangThaiDonHang(TrangThaiDonHang trangThaiDonHang) {
        this.trangThaiDonHang = trangThaiDonHang.toInt();
    }

    public int getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(int shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getMaVanDon() {
        return maVanDon;
    }

    public void setMaVanDon(String maVanDon) {
        this.maVanDon = maVanDon;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public TaiKhoan getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(TaiKhoan khachHang) {
        this.khachHang = khachHang;
    }
}
