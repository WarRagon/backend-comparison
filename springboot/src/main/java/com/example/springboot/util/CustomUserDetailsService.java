package com.example.springboot.util;

import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springboot.user.UserDTO;
import com.example.springboot.user.UserDomain;
import com.example.springboot.user.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
    private final UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("------loadUserByUsername------");

        UserDomain user = userRepository.getWithRoles(username);

        if(user == null) {
            throw new UsernameNotFoundException("Not Found");
        }

        UserDTO userDTO = new UserDTO(
            user.getEmail(),
            user.getPassword(),
            user.getNickname(),
            user.isSocial(),
            user.getUserRoleList().stream().map(userRole -> userRole.name()).collect(Collectors.toList()));
        log.info(userDTO);
        return userDTO;
    }
}
