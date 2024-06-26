package com.tutorial.authservice.service;

import com.tutorial.authservice.dto.AuthUserDto;
import com.tutorial.authservice.dto.RequestDto;
import com.tutorial.authservice.dto.TokenDto;
import com.tutorial.authservice.entity.User;
import com.tutorial.authservice.repository.AuthUserRepository;
import com.tutorial.authservice.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUserService {

    @Autowired
    AuthUserRepository authUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;

    /*
    public AuthUser save(NewUserDto dto) {
        Optional<AuthUser> user = authUserRepository.findByUserName(dto.getUserName());
        if(user.isPresent())
            return null;
        String password = passwordEncoder.encode(dto.getPassword());
        AuthUser authUser = AuthUser.builder()
                .userName(dto.getUserName())
                .password(password)
                .role(dto.getRole())
                .build();
        return authUserRepository.save(authUser);
    }

     */

    public void save(User user) {
        this.authUserRepository.save(user);
    }

    public Optional<User> getByUsernameOrEmail(String usernameOrEmail) {
        return authUserRepository.findByUserNameOrEmail(usernameOrEmail,usernameOrEmail);
    }

    public TokenDto login(AuthUserDto dto) {
        Optional<User> user = authUserRepository.findByUserNameOrEmail(dto.getUserName(),dto.getUserName());
        if(user.isEmpty())
            return null;
        if(passwordEncoder.matches(dto.getPassword(), user.get().getPassword()))
            return new TokenDto(jwtProvider.createToken(user.get()));
        return null;
    }

    public Optional<User> getByTokenPassword(String tokenPassword){
        return authUserRepository.findByTokenPassword(tokenPassword);
    }

    public TokenDto validate(String token, RequestDto dto) {
        if(!jwtProvider.validate(token, dto)) {
            return null;
        }
        String username = jwtProvider.getUserNameFromToken(token);
        if(authUserRepository.findByUserNameOrEmail(username,username).isEmpty()){
            return null;}
        System.out.println(token);
        return new TokenDto(token);
    }
}
