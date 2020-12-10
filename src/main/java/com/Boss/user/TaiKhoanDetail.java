package com.Boss.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TaiKhoanDetail implements UserDetails {
    TaiKhoan user;

    public TaiKhoanDetail(TaiKhoan user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

        switch (this.user.getLoaiTaiKhoan()){
            case Admin:
                list.add(new SimpleGrantedAuthority(LoaiTaiKhoan.Admin.toString()));
                break;
            case QuanLy:
                list.add(new SimpleGrantedAuthority(LoaiTaiKhoan.QuanLy.toString()));
                break;
            case NhanVien:
                list.add(new SimpleGrantedAuthority(LoaiTaiKhoan.NhanVien.toString()));
                break;
            case KhachHang:
                list.add(new SimpleGrantedAuthority(LoaiTaiKhoan.KhachHang.toString()));
                break;
        }

        return list;
    }

    public int getId() {
        return user.getId();
    }

    @Override
    public String getPassword() {
        return user.getMatKhau();
    }

    @Override
    public String getUsername() {
        return user.getTenDangNhap();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public TaiKhoan getUser(){
        return this.user;
    }
}
