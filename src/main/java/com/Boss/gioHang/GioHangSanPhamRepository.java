package com.Boss.gioHang;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface GioHangSanPhamRepository extends JpaRepository<GioHangSanPham, Integer> {
	
	@Modifying
    @Transactional
    @Query(value = "DELETE FROM GioHang_SanPham WHERE IDGIOHANG = ?1", nativeQuery = true)
    void deleteByGiohang(int giohangid);
	
	@Query(value = "DELETE FROM GioHang_SanPham WHERE IDSANPHAM = ?1", nativeQuery = true)
    void deleteBySP(int giohangid);
	
	@Query(value = "SELECT * FROM GioHang_SanPham gh WHERE gh.IDGIOHANG = ?1", nativeQuery = true)
    List<GioHangSanPham> findAllByGioHang(int giohang);
}
