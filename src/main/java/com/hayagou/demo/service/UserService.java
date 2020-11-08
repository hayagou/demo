package com.hayagou.demo.service;


import com.hayagou.demo.advice.exception.CEmailSigninFailedException;
import com.hayagou.demo.advice.exception.CUserNotFoundException;
import com.hayagou.demo.config.security.JwtTokenProvider;
import com.hayagou.demo.entity.User;
import com.hayagou.demo.model.dto.UserDto;
import com.hayagou.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public Authentication getAuthentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }

    public String signIn(String email, String password){
        User user = userRepository.findByEmail(email).orElseThrow(CEmailSigninFailedException::new);

        if(!passwordEncoder.matches(password, user.getPassword()))
            throw new CEmailSigninFailedException();

        return jwtTokenProvider.createToken(String.valueOf(user.getUserId()), user.getRoles());
    }

    public void signUp(String email, String password, String name){
        userRepository.save(User.builder().email(email).password(passwordEncoder.encode(password)).name(name).roles(Collections.singletonList("ROLE_USER")).build());
    }
    @Transactional(readOnly = true)
    public User getUser(String email){
        return userRepository.findByEmail(email).orElseThrow(CUserNotFoundException::new);
    }


    @Transactional(readOnly = true)
    public List<UserDto> getUsersList(){
        List<User> usersList = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<UserDto>();
        for(User user: usersList){
            userDtoList.add(new UserDto(user.getEmail(),user.getName()));
        }

        return userDtoList;
    }

    public void updatePassword(String email, String newPassword){
        userRepository.findByEmail(email).orElseThrow(CUserNotFoundException::new).updatePassword(passwordEncoder.encode(newPassword));
    }

    public void deleteUser(String email){
        userRepository.deleteByEmail(email);
    }

}
