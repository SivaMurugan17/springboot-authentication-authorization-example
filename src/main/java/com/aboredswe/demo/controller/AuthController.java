package com.aboredswe.demo.controller;

import com.aboredswe.demo.config.JWTUtil;
import com.aboredswe.demo.model.LoginPayload;
import com.aboredswe.demo.model.RegisterPayload;
import com.aboredswe.demo.model.Role;
import com.aboredswe.demo.model.User;
import com.aboredswe.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;
    private String jwtCookieName = "AuthCookie";

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterPayload registerPayload){
        User user = User.builder()
                .email(registerPayload.getEmail())
                .name(registerPayload.getName())
                .password(passwordEncoder.encode(registerPayload.getPassword()))
                .role(Role.USER)
                .build();
        userService.addUser(user);
        String token = jwtUtil.generateToken(user);
        ResponseCookie cookie = ResponseCookie.from(jwtCookieName,token).path("/api").maxAge(24*60*60).httpOnly(true).build();
        return  ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,cookie.toString()).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginPayload loginPayload){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginPayload.getEmail(),loginPayload.getPassword()));
        User user = userService.findUserByEmail(loginPayload.getEmail());
        String token = jwtUtil.generateToken(user);
        ResponseCookie cookie = ResponseCookie.from(jwtCookieName,token).path("/api").maxAge(24*60*60).httpOnly(true).build();
        return  ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,cookie.toString()).body(user);
    }

    @PostMapping("/logout")
    public ResponseEntity logout(){
        ResponseCookie cleanCookie =ResponseCookie.from(jwtCookieName,null).path("/api").build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,cleanCookie.toString()).body("You are logged out");
    }
}
