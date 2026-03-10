package com.example.pintbackend.repository;

import com.example.pintbackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  // 회원가입 - 기본 제공 save 사용
  boolean existsByEmail(String email);
}
