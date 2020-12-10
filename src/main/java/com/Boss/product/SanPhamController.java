package com.Boss.product;

import com.Boss.ResponseAPI;
import com.Boss.cuaHangSanPham.CuaHangSanPham;
import com.Boss.cuaHangSanPham.CuaHangSanPhamRepository;
import com.Boss.cuaHangSanPham.SoLuongSanPhamRequestData;
import com.Boss.danhmuc.DanhMuc;
import com.Boss.danhmuc.DanhMucRepository;
import com.Boss.ncc.NhaCungCap;
import com.Boss.ncc.NhaCungCapRepository;
import com.Boss.shop.CuaHang;
import com.Boss.shop.CuaHangRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/product")
public class SanPhamController {
    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private NhaCungCapRepository nccRepository;

    @Autowired
    private DanhMucRepository danhMucRepository;

    @Autowired
    private CuaHangRepository cuaHangRepository;

    @Autowired
    private CuaHangSanPhamRepository cuaHangSanPhamRepository;

    @GetMapping("{sanPhamId}")
    public ResponseEntity getSanPham(@PathVariable int sanPhamId){
        SanPham sp = this.sanPhamRepository.findById(sanPhamId).orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm có id = " + sanPhamId));

        SanPhamResponseData spr = new SanPhamResponseData(sp);

        ResponseAPI response = new ResponseAPI();
        response.Data = spr;

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('QUANLY')")
    public ResponseEntity taoSanPham(@ModelAttribute SanPhamRequestData req){
        SanPham sp = new SanPham();
        sp.setGiaSanPham(req.getGia());
        sp.setTenSanPham(req.getTenSanPham());
        sp.setMoTa(req.getMoTa());
        sp.setGiaSale(req.getGiaSale());

        String anh = this.uploadAnhSanPham(req);
        sp.setAnh(anh);

        NhaCungCap ncc = this.nccRepository.findById(req.getNhaCungCap()).orElseThrow(() -> new RuntimeException("Không tìm thấy ncc có id = " + req.getNhaCungCap()));
        sp.setNhaCungCap(ncc);

        DanhMuc dm = this.danhMucRepository.findById(req.getDanhMuc()).orElseThrow(() -> new RuntimeException("Không tìm thấy danh muc có id = " + req.getDanhMuc()));
        sp.setDanhMuc(dm);

        sp = this.sanPhamRepository.save(sp);

        ResponseAPI response = new ResponseAPI();
        response.Data = sp;

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("so-luong")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('QUANLY')")
    public ResponseEntity nhapSoLuongSanPham(@RequestBody List<SoLuongSanPhamRequestData> req){
        for (int i=0;i<req.size();i++){
            final SoLuongSanPhamRequestData reqData = req.get(i);
            CuaHangSanPham chsp;

            List<CuaHangSanPham> ds = this.cuaHangSanPhamRepository.findByCuaHangAndSanPham(reqData.getIdCuaHang(), reqData.getIdSanPham());
            if(ds.size() <= 0){
                chsp = new CuaHangSanPham();
            }else{
                chsp = ds.get(0);
            }

            chsp.setSoLuong(reqData.getSoLuong());

            CuaHang cuaHang = this.cuaHangRepository.findById(reqData.getIdCuaHang()).orElseThrow(() -> new RuntimeException("Không tìm thấy cửa hàng có id = " + reqData.getIdCuaHang()));
            SanPham sp = this.sanPhamRepository.findById(reqData.getIdSanPham()).orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm có id = " +reqData.getIdSanPham()));

            chsp.setCuaHang(cuaHang);
            chsp.setSanPham(sp);

            chsp = this.cuaHangSanPhamRepository.save(chsp);
        }

        ResponseAPI response = new ResponseAPI();
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("{sanPhamId}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('QUANLY')")
    public ResponseEntity editSanPham(@PathVariable int sanPhamId, @ModelAttribute SanPhamRequestData req){
        SanPham sp = this.sanPhamRepository.findById(sanPhamId).orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm có id = " + sanPhamId));
        sp.setTenSanPham(req.getTenSanPham());
        sp.setGiaSanPham(req.getGia());
        sp.setGiaSale(req.getGiaSale());
        sp.setMoTa(req.getMoTa());

        if(req.getAnh() != null){
            String anh = this.uploadAnhSanPham(req);
            sp.setAnh(anh);
        }

        DanhMuc dm = this.danhMucRepository.findById(req.getDanhMuc()).orElseThrow(() -> new RuntimeException("Không tìm thấy danh muc có id = " + req.getDanhMuc()));
        sp.setDanhMuc(dm);

        NhaCungCap ncc = this.nccRepository.findById(req.getNhaCungCap()).orElseThrow(() -> new RuntimeException("Không tìm thấy ncc có id = " + req.getNhaCungCap()));
        sp.setNhaCungCap(ncc);

        sp = this.sanPhamRepository.save(sp);

        ResponseAPI response = new ResponseAPI();
        response.Data = sp;

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @DeleteMapping("{sanPhamId}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('QUANLY')")
    public ResponseEntity xoaSanPham(@PathVariable int sanPhamId){
        ResponseAPI response = new ResponseAPI();

        SanPham sp = this.sanPhamRepository.findById(sanPhamId).orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm có id = " + sanPhamId));

        this.sanPhamRepository.delete(sp);

        response.Data = 1;

        return new ResponseEntity(response, HttpStatus.OK);
    }

    private String uploadAnhSanPham(SanPhamRequestData request){
        try {
            MultipartFile picture = request.getAnh();
            String fileName = picture.getOriginalFilename();
            File file = new File("C:/Users/ADMIN/OneDrive/Máy tính/api_pj/Nibosi-Web/src/assets/images/product", fileName);
            picture.transferTo(file);

            return fileName;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private File getProductFolderUpload() {
        ClassLoader classLoader = getClass().getClassLoader();
        File folderUpload = new File(classLoader.getResource(".").getFile()+"productImages");
        System.out.println("xxxyyyzzz "+ classLoader.getResource(".").getFile()+"productImages");
        if (!folderUpload.exists()) {
            folderUpload.mkdirs();
        }

        return folderUpload;
    }

}
