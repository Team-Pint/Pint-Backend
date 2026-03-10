package com.example.pintbackend.service;

import com.example.pintbackend.domain.User;
import com.example.pintbackend.exception.DuplicateEmailException;
import com.example.pintbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  // 회원가입 - 기본 제공 save 사용
  @Transactional
  public User createUser(User user) {
    if (userRepository.existsByEmail(user.getEmail())) {
      throw new DuplicateEmailException(user.getEmail());
    }
    return userRepository.save(user);
  }
}
