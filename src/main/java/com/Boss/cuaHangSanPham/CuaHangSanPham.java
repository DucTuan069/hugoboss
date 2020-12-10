package com.Boss.cuaHangSanPham;

import com.Boss.danhmuc.DanhMuc;
import com.Boss.product.SanPham;
import com.Boss.shop.CuaHang;

import javax.persistence.*;

@Entity
@Table(name = "CUAHANG_SANPHAM")
public class CuaHangSanPham {
    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name = "SOLUONG")
    private int soLuong;

    @ManyToOne
    @JoinColumn(name = "IDCUAHANG")
    private CuaHang cuaHang;

    @ManyToOne
    @JoinColumn(name = "IDSANPHAM")
    private SanPham sanPham;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public CuaHang getCuaHang() {
        return cuaHang;
    }

    public void setCuaHang(CuaHang cuaHang) {
        this.cuaHang = cuaHang;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }
}
