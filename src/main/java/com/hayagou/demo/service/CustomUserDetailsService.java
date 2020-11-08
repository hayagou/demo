package com.hayagou.demo.service;

import com.hayagou.demo.advice.exception.CUserNotFoundException;
import com.hayagou.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    // User 의 PK로 유저를 찾는 기능
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return userRepository.findById(Long.valueOf(userId)).orElseThrow(CUserNotFoundException::new);
    }
}
