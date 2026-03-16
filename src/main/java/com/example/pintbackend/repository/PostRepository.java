package com.example.pintbackend.repository;

import com.example.pintbackend.domain.post.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * Post 목록을 User와 함께 한번에 가져오기 (N+1 방지)
     * Step 1에서 findAll(pageable)로 ID만 뽑고,
     * Step 2에서 이 메서드로 User를 JOIN FETCH
     */
    @Query("SELECT p FROM Post p JOIN FETCH p.user WHERE p.id IN :ids")
    List<Post> findAllWithUser(@Param("ids") List<Long> ids);
}
