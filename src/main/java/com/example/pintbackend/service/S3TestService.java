/**
 * File: null.java
 * Path: com.example.pintbackend.service
 * <p>
 * Outline:
 * s3testservice
 * <p>
 * Author: jskt
 */

package com.example.pintbackend.service;


import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.UUID;


@Service
public class S3TestService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public S3TestService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    /**
     * MultipartFile 이미지를 S3에 업로드하고,
     * 브라우저에서 바로 조회 가능한 GET Pre-signed URL을 반환한다.
     */
    public String testUpload(MultipartFile file) throws IOException {
        // S3 객체 키(경로 + 파일명)를 생성한다.
        String path = buildObjectKey(file);

        // S3에 저장할 메타데이터를 파일 기준으로 맞춘다.
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        // MultipartFile의 실제 바이트를 S3에 업로드한다.
        amazonS3.putObject(bucket, path, file.getInputStream(), metadata);

        // 버킷이 private이어도 조회 가능한 임시 URL을 생성해서 반환한다.
        return getPresignedUrlToRead(path);
    }

    /**
     * 업로드된 객체를 읽기(GET) 위한 Pre-signed URL을 생성한다.
     * 이 URL은 expiration 시점까지만 유효하다.
     */
    public String getPresignedUrlToRead(String path) {
        // URL 만료 시간을 "현재 시각 + 5분"으로 설정한다.
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 5;
        expiration.setTime(expTimeMillis);

        // 조회 목적 URL이므로 반드시 GET 메서드로 서명해야 한다.
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, path)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);

        // AWS 서명 정보가 포함된 임시 접근 URL 생성
        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);

        return url.toString();
    }

    /**
     * S3 객체 키를 생성한다.
     * - 기본 폴더: images/
     * - 파일명 충돌 방지: UUID 사용
     * - 원본 확장자 유지(.png, .jpg 등)
     */
    private String buildObjectKey(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null) {
            // 파일명에 '.'이 있으면 마지막 '.' 이후를 확장자로 사용한다.
            int dotIndex = originalFilename.lastIndexOf(".");
            if (dotIndex >= 0) {
                extension = originalFilename.substring(dotIndex);
            }
        }
        return "images/profileImage-" + UUID.randomUUID() + extension;
    }
}
