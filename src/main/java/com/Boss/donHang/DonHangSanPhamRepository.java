package com.Boss.donHang;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface DonHangSanPhamRepository extends JpaRepository<DonHangSanPham, Integer> {
    @Query(
            value = "SELECT * FROM DONHANG_SANPHAM dhsp WHERE dhsp.IdCuaHang = ?1",
            nativeQuery = true)
    List<DonHangSanPham> findByIdCuaHang(int cuaHangId);

    @Query(
            value = "SELECT * FROM DONHANG_SANPHAM dhsp WHERE dhsp.IdDonHang = ?1",
            nativeQuery = true)
    Optional<List<DonHangSanPham>> findByIdDonHang(int donHangId);

    @Query(
            value = "SELECT * FROM DONHANG_SANPHAM dhsp WHERE dhsp.IdCuaHang = ?1 AND dhsp.NgayTao >= ?2 AND dhsp.NgayTao <= ?3",
            nativeQuery = true)
    List<DonHangSanPham> findByIdCuaHangAndDateRange(int cuaHangId, String from, String to);
}
