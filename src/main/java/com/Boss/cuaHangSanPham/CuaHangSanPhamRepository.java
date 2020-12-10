package com.Boss.cuaHangSanPham;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CuaHangSanPhamRepository extends JpaRepository<CuaHangSanPham, Integer> {
    @Query(
            value = "SELECT * FROM CUAHANG_SANPHAM chsp WHERE chsp.idCuaHang = ?1 AND chsp.idSanPham = ?2",
            nativeQuery = true)
    List<CuaHangSanPham> findByCuaHangAndSanPham(int idCuaHang, int idSanPham);
}
