package com.example.pintbackend.dto.postDto;

public record PostUserInfo(
    Long userId,
    String nickname,
    String profileImage,
    Boolean isWriter
) {

}
