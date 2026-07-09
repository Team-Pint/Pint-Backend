package com.example.pintbackend.dto.postDto;

import com.example.pintbackend.domain.post.Post;
import com.fasterxml.jackson.annotation.JsonProperty;

public record PostImageResponse(
        Long id,
        Long height,
        Long width,
        String imageUrl,
        String camera,
        String location,
        int likeCount,
        @JsonProperty("isLiked") boolean isLiked,
        PostUserInfo userInfo
) {

    public static PostImageResponse from(Post post, int likeCount, boolean isLiked, String imageUrl, PostUserInfo userInfo) {
        return new PostImageResponse(
                post.getId(),
                post.getHeight(),
                post.getWidth(),
                imageUrl,
                post.getCamera(),
                post.getLocation(),
                likeCount,
                isLiked,
                userInfo
        );
    }
}
