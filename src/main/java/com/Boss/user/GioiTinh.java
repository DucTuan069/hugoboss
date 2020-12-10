package com.Boss.user;

import com.Boss.ResponseAPI;
import com.Boss.security.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

public enum GioiTinh {
    Nam(0), Nu(1);

    private int value;

    GioiTinh(int value) {
        this.value = value;
    }

    public int toInt(){
        return this.value;
    }

    public static GioiTinh valueOf(int gioiTinh) {
        return GioiTinh.values()[gioiTinh];
    }

    @Override
    public String toString() {
        switch (this) {
            case Nam: return "Nam";
            case Nu: return "Nữ";
        }

        return super.toString();
    }
}
