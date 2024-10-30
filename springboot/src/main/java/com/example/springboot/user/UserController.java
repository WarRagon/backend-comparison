package com.example.springboot.user;

//import java.io.IOException;
import java.util.Map;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.user.UserDTO.LoginRequestDTO;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/user")
public class UserController {    
    
    //@Autowired
    //private AuthenticationManager authenticationManager;

    //@Autowired
    //private AuthenticationSuccessHandler successHandler;


    @PostMapping("/login")
    public Map<String, String> login(
        @RequestBody LoginRequestDTO loginRequestDTO
        /*
        @RequestBody LoginRequestDTO loginRequestDTO,
        HttpServletRequest request,
        HttpServletResponse response
        */
    ) {
        return Map.of("RESULT", "SUCCESS");
    }
}
