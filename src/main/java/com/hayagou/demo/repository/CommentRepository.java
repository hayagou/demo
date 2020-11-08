package com.hayagou.demo.repository;

import com.hayagou.demo.entity.Comment;
import com.hayagou.demo.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findByPost(Post post);
}
