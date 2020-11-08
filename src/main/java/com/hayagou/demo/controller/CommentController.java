package com.hayagou.demo.controller;


import com.hayagou.demo.entity.Comment;
import com.hayagou.demo.entity.Post;
import com.hayagou.demo.entity.User;
import com.hayagou.demo.model.dto.CommentDto;
import com.hayagou.demo.model.response.CommonResult;
import com.hayagou.demo.model.response.ListResult;
import com.hayagou.demo.model.response.SingleResult;
import com.hayagou.demo.service.BoardService;
import com.hayagou.demo.service.CommentService;
import com.hayagou.demo.service.ResponseService;
import com.hayagou.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "4. Comment")
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;
    private final ResponseService responseService;
    private final BoardService boardService;

    // 댓글쓰기
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "댓글 작성", notes = "게시판에 댓글을 작성한다.")
    @PostMapping(value = "/comment/{postId}")
    public SingleResult<Comment> writePost(@PathVariable long postId, @Valid @ModelAttribute CommentDto commentDto){
        String email = userService.getAuthentication().getName();
        User user = userService.getUser(email);
        Post post = boardService.getPost(postId);

        return responseService.getSingleResult(commentService.writeComment(user, post, commentDto));
    }

    // findBypostId comment list
    @ApiOperation(value = "댓글 조회", notes = "게시글에 맞는 댓글 목록을 출력한다.")
    @GetMapping(value = "/comments/{postId}")
    public ListResult<Comment> getComments(@PathVariable long postId){
        Post post = boardService.getPost(postId);
        return responseService.getListResult(commentService.getCommentListByPost(post));
    }

    // 댓글 수정
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "댓글 수정", notes = "게시판에 댓글을 수정한다.")
    @PutMapping(value = "/comment/{commentId}")
    public SingleResult<Comment> updateComment(@PathVariable long commentId, @Valid @ModelAttribute CommentDto commentDto){
        String email = userService.getAuthentication().getName();
        return responseService.getSingleResult(commentService.updateComment(email , commentId, commentDto));
    }

    // 댓글 삭제
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "댓글 수정", notes = "게시판에 댓글을 수정한다.")
    @DeleteMapping(value = "/comment/{commentId}")
    public CommonResult deleteComment(@PathVariable long commentId){
        String email = userService.getAuthentication().getName();
        commentService.deleteComment(email , commentId);
        return responseService.getSuccessResult();
    }
}