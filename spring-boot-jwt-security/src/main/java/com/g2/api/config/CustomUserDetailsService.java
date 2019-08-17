package com.g2.api.config;

import com.g2.api.model.ExecuteDao;
import com.g2.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ExecuteDao executeDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
           // User user = executeDao.validateLogin(username);
            User user = new User();
            user.setUsername(username);
            user.setPassword("1234");
            if (user == null) {
                throw new UsernameNotFoundException("Username: " + username + " not found");
            }
            List<SimpleGrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority("USER"));
            return new org.springframework.security.core.userdetails.User(username, user.getPassword(), roles);
        } catch (Exception ex) {
            throw new UsernameNotFoundException("Username: " + username + " not found");
        }
    }
}