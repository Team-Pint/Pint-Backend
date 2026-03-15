package com.example.pintbackend.dto.search;

import com.example.pintbackend.domain.user.entity.User;

public record SearchUser(
        Long userId,
        String username,
        String profileImage
) {
    public static SearchUser from(User user, String profileImage) {
        return new SearchUser(
                user.getId(),
                user.getUsername(),
                profileImage
        );
    }
}
