package com.Boss.donHang;

public enum TrangThaiDonHang {
    HuyBo(0) , TiepNhan(1), DaThanhToan(2), ThanhToanThatBai(3), ChoThanhToan(4), HoanTra(5), DangVanChuyen(6), ThanhCong(7);

    private int value;

    TrangThaiDonHang(int value) {
        this.value = value;
    }

    public int toInt(){
        return this.value;
    }

    public static TrangThaiDonHang valueOf(int stt) {
        return TrangThaiDonHang.values()[stt];
    }

    @Override
    public String toString() {
        switch (this) {
            case HuyBo: return "Hủy bỏ";
            case TiepNhan: return "Tiếp nhận";
            case DaThanhToan: return "Đã thanh toán";
            case ThanhToanThatBai: return "Thanh toán thất bại";
            case ChoThanhToan: return "Chờ thanh toán";
            case HoanTra: return "Hoàn trả";
            case DangVanChuyen: return "Đang vận chuyển";
            case ThanhCong: return "Thành công";
        }

        return super.toString();
    }
}
