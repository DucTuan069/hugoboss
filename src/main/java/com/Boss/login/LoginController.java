package com.Boss.login;

import com.Boss.ResponseAPI;
import com.Boss.security.JwtTokenProvider;
import com.Boss.user.TaiKhoanDetail;
import com.Boss.user.TaiKhoanRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/login")
public class LoginController {
    private final TaiKhoanRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public LoginController(TaiKhoanRepository taiKhoanRepository, PasswordEncoder passwordEncoder) {
        this.repository = taiKhoanRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("")
    public ResponseEntity login(@RequestBody LoginRequestData request){
        ResponseAPI response = new ResponseAPI();

        try {
            // Xác thực từ username và password.
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Trả về jwt cho user.
            String jwt = tokenProvider.generateToken((TaiKhoanDetail) authentication.getPrincipal());

            response.Data = jwt;
            return new ResponseEntity(response, HttpStatus.OK);

        }catch (Exception e){
            response.Code = -1;
            //            response.Message = "Thông tin đăng nhập không đúng!";
            response.Message = e.getMessage();
            return new ResponseEntity(response, HttpStatus.OK);
        }
    }
}
