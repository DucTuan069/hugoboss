package com.Boss.donHang;

import com.Boss.product.SanPham;
import com.Boss.shop.CuaHang;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "DONHANG_SANPHAM")
public class DonHangSanPham {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "IDDONHANG")
//    @JsonIgnore
    private DonHang donHang;

    @ManyToOne
    @JoinColumn(name = "IDSANPHAM")
    private SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "IDCUAHANG")
    private CuaHang cuaHang;

    @Column(name = "SOLUONG")
    private int soLuong;

    @Column(name = "NGAYTAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayTao;

    @PrePersist
    void ngayTao() {
        this.ngayTao = new Date();
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DonHang getDonHang() {
        return donHang;
    }

    public void setDonHang(DonHang donHang) {
        this.donHang = donHang;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public CuaHang getCuaHang() {
        return cuaHang;
    }

    public void setCuaHang(CuaHang cuaHang) {
        this.cuaHang = cuaHang;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}