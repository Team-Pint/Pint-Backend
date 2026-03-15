package com.example.pintbackend.controller;

import com.example.pintbackend.dto.search.SearchUser;
import com.example.pintbackend.dto.common.response.BaseResponse;
import com.example.pintbackend.dto.search.SearchUserResponse;
import com.example.pintbackend.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<BaseResponse<SearchUserResponse>> fetchSearchResult(
        @RequestParam(value = "keyword", required = false) String keyword
    ) {
        List<SearchUser> userList = userService.getUserByQuery(keyword);
        SearchUserResponse response = new SearchUserResponse(userList);

        return ResponseEntity.ok(BaseResponse.success(response));
    }
}
