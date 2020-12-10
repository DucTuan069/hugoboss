package com.Boss.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class TaiKhoanService implements UserDetailsService {

    @Autowired
    private TaiKhoanRepository repository;

    public TaiKhoanDetail loadByUserId(int userId) throws UsernameNotFoundException {
        TaiKhoan user = repository.findById(userId).orElseThrow(() -> new RuntimeException("User not found: " + userId));
        TaiKhoanDetail tkd = new TaiKhoanDetail(user);

        return tkd;
    }

    @Override
    public TaiKhoanDetail loadUserByUsername(String username) throws UsernameNotFoundException {
        TaiKhoan user = repository.findByTenDangNhap(username).orElseThrow(() -> new RuntimeException("User not found: " + username));
        TaiKhoanDetail tkd = new TaiKhoanDetail(user);
        return tkd;
    }
}
