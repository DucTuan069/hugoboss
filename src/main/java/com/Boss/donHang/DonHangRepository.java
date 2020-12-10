package com.Boss.donHang;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DonHangRepository extends JpaRepository<DonHang, Integer> {
    @Query(
            value = "SELECT * FROM DONHANG dh WHERE dh.NgayTao >= ?1 AND dh.NgayTao <= ?2",
            nativeQuery = true)
    List<DonHang> findAllByDateRange(String from, String to);

    @Query(
            value = "SELECT * FROM DONHANG dh WHERE dh.NgayTao >= ?2 AND dh.NgayTao <= ?3 AND IdTaiKhoan = ?1",
            nativeQuery = true)
    List<DonHang> findAllByUserAndDateRange(int userId, String from, String to);
}
