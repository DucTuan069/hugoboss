package com.Boss.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, Integer> {
    Optional<TaiKhoan> findByTenDangNhap(String tenDangNhap);

//    Page<TaiKhoan> findByEmailContains(String email, Pageable pageable);
//    Page<TaiKhoan> findAllByEmail(String email, Pageable pageable);
//    Page<TaiKhoan> findAllByEmailContainsAndEmail(String email, String auth, Pageable pageable);

//    Boolean existsByEmail(String email);
}
