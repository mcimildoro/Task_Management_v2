package com.leanxcale.testapp.controller;

import com.leanxcale.testapp.model.User;
import com.leanxcale.testapp.repository.UserRepository;
import com.leanxcale.testapp.security.JwtUtil;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        System.out.println("🔥 Llegó al endpoint de REGISTER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> userData,
            HttpServletResponse response) {
        String username = userData.get("username");
        String password = userData.get("password");

        Authentication auth = authenticationManager.authenticate( // "Quiero autenticar a alguien con este username y
                                                                  // esta contraseña."
                new UsernamePasswordAuthenticationToken(username, password));

        System.out.println("Authenticated: " + auth.isAuthenticated());

        if (auth.isAuthenticated()) { // Si la autenticación fue exitosa
            String token = jwtUtil.generateToken(username); // Generamos el token JWT para el usuario autenticado
            ResponseCookie cookie = ResponseCookie.from("token", token)
                    .httpOnly(true) // Solo HTTP, no accesible desde JS
                    .secure(true) // Solo HTTPS, no accesible desde HTTP
                    .path("/") // Solo accesible desde la raíz de la aplicación
                    .maxAge(60 * 60 * 24) // Expira en 24 horas
                    .sameSite("Lax") // Solo accesible desde el mismo sitio
                    .build(); // Crear la cookie

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return ResponseEntity.ok(Map.of("message", "Login successful"));
        } else {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // ✅ Sobrescribimos la cookie con expiración 0
        ResponseCookie cookie = ResponseCookie.from("token", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(Map.of("message", "Logout successful"));
    }

}
