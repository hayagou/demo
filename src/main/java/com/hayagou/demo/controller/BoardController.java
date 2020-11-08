package com.hayagou.demo.controller;


import com.hayagou.demo.entity.Post;
import com.hayagou.demo.model.dto.BoardDto;
import com.hayagou.demo.model.dto.PostDto;
import com.hayagou.demo.model.response.CommonResult;
import com.hayagou.demo.model.response.ListResult;
import com.hayagou.demo.model.response.SingleResult;
import com.hayagou.demo.service.BoardService;
import com.hayagou.demo.service.ResponseService;
import com.hayagou.demo.service.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Api(tags = {"3. Board"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/board")
public class BoardController {

    private final BoardService boardService;
    private final ResponseService responseService;
    private final UserService userService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "게시판 생성 (관리자 권한)", notes = "게시판을 생성한다.")
    @PostMapping
    public SingleResult<BoardDto> createBoard(@ApiParam(value = "게시판명", required = true)  @Valid @ModelAttribute BoardDto boardDto){
        return responseService.getSingleResult(boardService.createBoard(boardDto));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "게시판 글 작성", notes = "게시판에 글을 작성한다.")
    @PostMapping(value = "/{boardName}")
    public SingleResult<Post> writePost(@PathVariable String boardName, @Valid @ModelAttribute PostDto postDto){
        String email = userService.getAuthentication().getName();

        return responseService.getSingleResult(boardService.writePost(email,boardName,postDto));
    }

    @ApiOperation(value = "게시판 글 리스트", notes = "게시판 게시글 리스트를 조회한다.")
    @GetMapping(value = "{boardName}/posts")
    public ListResult<Post> getPosts(@PathVariable String boardName){
        return responseService.getListResult(boardService.getPostsList(boardName));
    }

    @ApiOperation(value = "게시판 글 상세", notes = "게시판 글 상세 정보를 조회한다.")
    @GetMapping(value = "/post/{postId}")
    public SingleResult<Post> getPost(@PathVariable long postId){
        return responseService.getSingleResult(boardService.getPost(postId));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "게시판 글 수정", notes = "게시판 글을 수정한다.")
    @PutMapping(value = "/post/{postId}")
    public SingleResult<Post> updatePost(@PathVariable long postId, @Valid @ModelAttribute PostDto postDto){
        String email = userService.getAuthentication().getName();
        return responseService.getSingleResult(boardService.updatePost(postId,email,postDto));
    }
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "게시판 글 삭제", notes = "게시판 글을 삭제한다.")
    @DeleteMapping(value = "/post/{postId}")
    public CommonResult deletePost(@PathVariable long postId){
        String email = userService.getAuthentication().getName();
        boardService.deletePost(postId, email);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "게시글 검색(고쳐야함)", notes = "type = all, title, author, content 기본값 : all ")
    @GetMapping(value = "/{boardName}/posts/{type}/{keword}")
    public ListResult<Post> searchPost(@PathVariable String boardName, @PathVariable String type, @PathVariable String keword){
        return null;
//        return responseService.getListResult(boardService.searchPost(boardName, type, keword));
    }

    @ApiOperation(value = "게시글 페이징", notes = "페이징처리된 게시글 리스트 조회, 페이지당 게시글수 10개")
    @GetMapping(value = "/{boardName}/posts/{page}")
    public ListResult<Post> getPosts(@PathVariable String boardName, @PathVariable @Min(0) int page){

        return responseService.getListResult(boardService.getPostsList(boardName, page));
    }
}