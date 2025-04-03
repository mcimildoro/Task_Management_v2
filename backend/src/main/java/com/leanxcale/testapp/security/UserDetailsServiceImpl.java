package com.leanxcale.testapp.security;

import com.leanxcale.testapp.model.User;
import com.leanxcale.testapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import org.springframework.security.core.GrantedAuthority;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

            return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
            new ArrayList<GrantedAuthority>()
        );
    }
}


