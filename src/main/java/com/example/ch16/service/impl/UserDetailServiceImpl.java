package com.example.ch16.service.impl;

import com.example.ch16.repository.UserRepository;
import com.example.ch16.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // 자동으로 빈 등록하려면 세팅
@RequiredArgsConstructor // 필수 매개변수를 생성자에 넣고 만들어줌.
public class UserDetailServiceImpl implements UserDetailService {

    private final UserRepository userRepository; // 생성자에 주입 및 autowired해야 함.

//    // @RequiredArgsConstructor 어노테이션 선언하면 아래 생성자 생략 가능
//    @Autowired
//    public UserDetailServiceImpl(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getByUid(username);
    }
}
