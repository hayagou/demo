package com.hayagou.demo.repository;

import com.hayagou.demo.entity.Board;
import com.hayagou.demo.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByBoard(Board board);
    Page<Post> findByBoard(Board board, Pageable pageable);

//    List<Post> findByBoardAndPostsByTitleContaining(Board board, String keword);
//    // 내용
//    List<Post> findByBoardAndPostsByContentContaining(Board board, String keword);
//    // 작성자
//    List<Post> findByBoardAndPostsByAuthorContaining(Board board, String keword);
//    // 제목 + 내용 검색
//    List<Post> findByBoardAndPostsByTitleContainingOrContentContaining(Board board, String keword);

}