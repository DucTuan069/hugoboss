package com.Boss.gioHang;

import com.Boss.user.TaiKhoan;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "GIOHANG")
public class GioHang {
    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "IDTAIKHOAN")
    private TaiKhoan kh;

    @OneToMany(mappedBy = "gioHang")
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<GioHangSanPham> dsGioHangSanPham;

    public List<GioHangSanPham> getDsGioHangSanPham() {
        return dsGioHangSanPham;
    }

    public void setDsGioHangSanPham(List<GioHangSanPham> dsGioHangSanPham) {
        this.dsGioHangSanPham = dsGioHangSanPham;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TaiKhoan getKhachHang() {
        return kh;
    }

    public void setKhachHang(TaiKhoan khachHang) {
        this.kh = khachHang;
    }
}
