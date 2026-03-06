/**
 * domain/Image.java
 * 용도: 이미지 entity 구현,
 * 필드: imgUrl, s3Key, size, width, height
 * <p>
 * Last Updated: Junsung Kim
 */

package com.example.pintbackend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "images")
@Getter
@Setter
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String imgUrl;

}


