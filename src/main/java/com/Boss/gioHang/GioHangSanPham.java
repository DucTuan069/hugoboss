package com.Boss.gioHang;

import com.Boss.product.SanPham;
import com.Boss.shop.CuaHang;

import javax.persistence.*;

@Entity
@Table(name = "GIOHANG_SANPHAM")
public class GioHangSanPham {
    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "IDSANPHAM")
    private SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "IDGIOHANG")
    private GioHang gioHang;

    @Column(name = "SOLUONG")
    private int soLuong;

    @ManyToOne
    @JoinColumn(name = "IDCUAHANG")
    private CuaHang cuaHang;

    public CuaHang getCuaHang() {
        return cuaHang;
    }

    public void setCuaHang(CuaHang cuaHang) {
        this.cuaHang = cuaHang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public GioHang getGioHang() {
        return gioHang;
    }

    public void setGioHang(GioHang gioHang) {
        this.gioHang = gioHang;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
