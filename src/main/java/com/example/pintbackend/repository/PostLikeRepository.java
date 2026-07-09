package com.example.pintbackend.repository;

import com.example.pintbackend.domain.PostLike;
import com.example.pintbackend.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    Optional<PostLike> findByPostIdAndUserId(Long postId, Long userId);

    boolean existsByPostIdAndUserId(Long postId, Long userId);

    int countByPostId(Long postId);

    // 유저 아이디 마다 좋아요 한 포스트 불러오기
    @Query("""
            SELECT p FROM PostLike pl
            JOIN pl.post p
            WHERE pl.user.id = :userId
            """)
    List<Post> findAllLikedPostByUserId(@Param("userId") Long userId);

    // 주어진 게시글들 중 이 유저가 좋아요한 게시글 ID들 (배치 조회)
    @Query("""
            SELECT pl.post.id FROM PostLike pl
            WHERE pl.post.id IN :postIds AND pl.user.id = :userId
            """)
    Set<Long> findLikedPostIdsByUser(@Param("postIds") List<Long> postIds, @Param("userId") Long userId);

    // 게시글별 좋아요 개수 (배치 조회)
    @Query("""
            SELECT pl.post.id, COUNT(pl) FROM PostLike pl
            WHERE pl.post.id IN :postIds
            GROUP BY pl.post.id
            """)
    List<Object[]> countByPostIds(@Param("postIds") List<Long> postIds);

}
