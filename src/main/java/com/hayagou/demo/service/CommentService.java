package com.hayagou.demo.service;

import com.hayagou.demo.advice.exception.CNotOwnerException;
import com.hayagou.demo.advice.exception.CResourceNotExistException;
import com.hayagou.demo.entity.Comment;
import com.hayagou.demo.entity.Post;
import com.hayagou.demo.entity.User;
import com.hayagou.demo.model.dto.CommentDto;
import com.hayagou.demo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    //댓글 쓰기
    public Comment writeComment(User user, Post post, CommentDto commentDto){
        Comment comment = new Comment(user, post , commentDto.getContent());
        return commentRepository.save(comment);
    }

    //포스트에 대한 댓글 조회
    public List<Comment> getCommentListByPost(Post post){
        return commentRepository.findByPost(post);
    }

    // 댓글 단건 조회
    public Comment getComment(long commentId){
        return commentRepository.findById(commentId).orElseThrow(CResourceNotExistException::new);
    }


    public Comment updateComment(String email, long commentId, CommentDto commentDto){
        Comment comment = getComment(commentId);
        User user = comment.getUser();
        if(!email.equals(user.getEmail()))
            throw new CNotOwnerException();
        return comment.updateComment(commentDto.getContent());
    }

    // 댓글 삭제
    public boolean deleteComment(String email, long commentId){
        Comment comment = getComment(commentId);
        User user = comment.getUser();
        if(!email.equals(user.getEmail()))
            throw new CNotOwnerException();
        commentRepository.delete(comment);
        return true;
    }
}
