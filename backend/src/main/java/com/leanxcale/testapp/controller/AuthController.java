package com.leanxcale.testapp.controller;

import com.leanxcale.testapp.model.User;
import com.leanxcale.testapp.repository.UserRepository;
import com.leanxcale.testapp.security.JwtUtil;
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
    public Map<String, String> login(@RequestBody Map<String, String> userData) {
        String username = userData.get("username"); 
        String password = userData.get("password"); 

        Authentication auth = authenticationManager.authenticate( // "Quiero autenticar a alguien con este username y esta contraseña."
                new UsernamePasswordAuthenticationToken(username, password));

        System.out.println("Authenticated: " + auth.isAuthenticated()); 

        String token = jwtUtil.generateToken(username); // Generamos el token JWT para el usuario autenticado


        return Map.of("token", token); // Devolvemos el token en el cuerpo de la respuesta
    }
}
