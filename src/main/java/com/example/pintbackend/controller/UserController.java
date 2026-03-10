package com.example.pintbackend.controller;

import com.example.pintbackend.domain.User;
import com.example.pintbackend.dto.common.response.BaseResponse;
import com.example.pintbackend.dto.post.request.CreateUserRequest;
import com.example.pintbackend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping()
  public ResponseEntity<BaseResponse<?>> createUser(
      @Valid @RequestBody CreateUserRequest request
  ) {
    User user = request.toEntity();

    User savedUser = userService.createUser(user);

    return ResponseEntity.ok(BaseResponse.success(""));
  }
}
