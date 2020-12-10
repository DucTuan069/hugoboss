package com.Boss.gioHang;

import com.Boss.ResponseAPI;
import com.Boss.cuaHangSanPham.SoLuongSanPhamRequestData;
import com.Boss.donHang.DonHangRequestData;
import com.Boss.product.SanPham;
import com.Boss.product.SanPhamRepository;
import com.Boss.shop.CuaHang;
import com.Boss.shop.CuaHangRepository;
import com.Boss.user.TaiKhoan;
import com.Boss.user.TaiKhoanDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/gio-hang")
public class GioHangController {

	@Autowired
	private GioHangRepository gioHangRepository;

	@Autowired
	private SanPhamRepository sanPhamRepository;

	@Autowired
	private CuaHangRepository cuaHangRepository;

	@Autowired
	GioHangSanPhamRepository gioHangSanPhamRepository;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<ResponseAPI> getGioHang() {
		ResponseAPI res = new ResponseAPI();

		TaiKhoanDetail user = (TaiKhoanDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		TaiKhoan khachHang = user.getUser();

		List<GioHang> gh = this.gioHangRepository.findAllByUser(khachHang.getId());
		if (gh.size() == 0) {
			res.Data = new ArrayList();
			return new ResponseEntity(res, HttpStatus.OK);
		}

		List<GioHangSanPham> ghsp = gh.get(0).getDsGioHangSanPham();
		res.Data = ghsp;

		return new ResponseEntity(res, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<ResponseAPI> taoGioHang(@RequestBody DonHangRequestData data) {
		ResponseAPI res = new ResponseAPI();

		TaiKhoanDetail user = (TaiKhoanDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		TaiKhoan khachHang = user.getUser();
		System.out.println(khachHang.getId());
		List<GioHang> giohang= this.gioHangRepository.findAllByUser(khachHang.getId());
        
        this.gioHangSanPhamRepository.deleteByGiohang(giohang.get(0).getId());

		this.gioHangRepository.deleteByTaiKhoan(khachHang.getId());

		GioHang gh = new GioHang();
		gh.setKhachHang(khachHang);
		gh = this.gioHangRepository.save(gh);

		List<SoLuongSanPhamRequestData> dsSanPham = data.getSanPham();
		for (int i = 0; i < dsSanPham.size(); i++) {
			SoLuongSanPhamRequestData sp = dsSanPham.get(i);
			GioHangSanPham ghsp = new GioHangSanPham();
			ghsp.setSoLuong(sp.getSoLuong());
			ghsp.setGioHang(gh);

			SanPham p = this.sanPhamRepository.findById(sp.getIdSanPham())
					.orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm có id = " + sp.getIdSanPham()));
			ghsp.setSanPham(p);

			CuaHang ch = this.cuaHangRepository.findById(sp.getIdCuaHang())
					.orElseThrow(() -> new RuntimeException("Không tìm thấy cửa hàng có id = " + sp.getIdCuaHang()));
			ghsp.setCuaHang(ch);

			ghsp = this.gioHangSanPhamRepository.save(ghsp);
		}

		return new ResponseEntity(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/xoa", method = RequestMethod.GET)
    public ResponseEntity<ResponseAPI> xoaGioHang(@RequestParam int item){
        ResponseAPI res = new ResponseAPI();

        TaiKhoanDetail user = (TaiKhoanDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TaiKhoan khachHang = user.getUser();
        
        this.gioHangSanPhamRepository.deleteBySP(item);
        List<GioHang> gh = this.gioHangRepository.findAllByUser(khachHang.getId());
        List<GioHangSanPham> ghsp = this.gioHangSanPhamRepository.findAllByGioHang(gh.get(0).getId());
        
        if(ghsp.size()==0) {
        	this.gioHangRepository.deleteByTaiKhoan(khachHang.getId());
        }
        res.Data = gh;
        return new ResponseEntity(res, HttpStatus.OK);
        
    }

}
