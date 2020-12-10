package com.Boss.gioHang;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface GioHangRepository extends JpaRepository<GioHang, Integer> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM GioHang WHERE IdTaiKhoan = ?1", nativeQuery = true)
    void deleteByTaiKhoan(int khachHangId);

    @Query(value = "SELECT * FROM GIOHANG gh WHERE gh.IdTaiKhoan = ?1", nativeQuery = true)
    List<GioHang> findAllByUser(int khachHang);
}
