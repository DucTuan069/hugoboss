package com.Boss.user;

public enum LoaiTaiKhoan {
    Admin(0), QuanLy(1), NhanVien(2), KhachHang(3);

    private int value;

    LoaiTaiKhoan(int value) {
        this.value = value;
    }

    public int toInt(){
        return this.value;
    }

    public static LoaiTaiKhoan valueOf(int role) {
        return LoaiTaiKhoan.values()[role];
    }

    @Override
    public String toString() {
        switch (this) {
            case Admin: return "ADMIN";
            case QuanLy: return "QUANLY";
            case NhanVien: return "NHANVIEN";
            case KhachHang: return "CUSTOMER";
        }

        return super.toString();
    }
}
