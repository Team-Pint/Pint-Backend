package com.example.pintbackend.dto.search;

import java.util.List;

public record SearchUserResponse(
        List<SearchUser> userList
) {

}
