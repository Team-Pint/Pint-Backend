package com.example.pintbackend.dto.user.response;

/**
 * CSRF 토큰 raw 문자열 응답 DTO.
 *
 * @param csrfToken 쿠키(XSRF-TOKEN)와 동일한 값이어야 하는 CSRF 토큰 문자열
 */
public record CsrfTokenResponse(
    String csrfToken
) {
}

