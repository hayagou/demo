package com.hayagou.demo.service;


import com.hayagou.demo.advice.exception.CNotOwnerException;
import com.hayagou.demo.advice.exception.CResourceNotExistException;
import com.hayagou.demo.advice.exception.CUserNotFoundException;
import com.hayagou.demo.entity.Board;
import com.hayagou.demo.entity.Post;
import com.hayagou.demo.entity.User;
import com.hayagou.demo.model.dto.BoardDto;
import com.hayagou.demo.model.dto.PostDto;
import com.hayagou.demo.repository.BoardRepository;
import com.hayagou.demo.repository.PostRepository;
import com.hayagou.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 블럭에 존재하는 페이지 번호 수
    private static final int BLOCK_PAGE_NUM_COUNT = 5;
    // 한 페이지에 존재하는 게시글 수
    private static final int PAGE_POST_COUNT = 4;

    private Board findBoard(String boardName){
        return Optional.ofNullable(boardRepository.findByName(boardName)).orElseThrow(CResourceNotExistException::new);
    }

    // 게시판 추가
    public BoardDto createBoard(BoardDto boardDto){
        boardRepository.save(boardDto.toEntity());
        return boardDto;
    }

    // 게시판 이름 수정
    public Board updateBoard(String boardName, String newBoardName){
        Board board = findBoard(boardName);
        return board.updateBaordName(newBoardName);
    }

    // 게시판 삭제
    public void deleteBoard(String boardName){
        boardRepository.deleteByName(boardName);
    }

    // 글작성
    public Post writePost(String email, String boardName, PostDto postDto){
        Board board = findBoard(boardName);
        User user = userRepository.findByEmail(email).orElseThrow(CUserNotFoundException::new);
        Post post = new Post(postDto.getTitle(), postDto.getContent(), board, user);
        return postRepository.save(post);
    }

    // 게시판 이름으로 게시물 리스트 조회
    public List<Post> getPostsList(String boardName){
        return postRepository.findByBoard(findBoard(boardName));
    }

    // 게시판 이름으로 게시물 리스트 조회 페이지네이션 추가
    public List<Post> getPostsList(String boardName, int page){

        Page<Post> pagePosts = postRepository.findByBoard(findBoard(boardName), PageRequest.of(page-1, 2, Sort.by(Sort.Direction.DESC, "createdDate")));
        return pagePosts.getContent();
    }

    // posdtId로 post 단건 조회
    public Post getPost(long postId){
        return postRepository.findById(postId).orElseThrow(CResourceNotExistException::new);
    }

    public Post updatePost(long postId, String email, PostDto postDto){
        Post post = getPost(postId);
        User user = post.getUser();

        if(!email.equals(user.getEmail()))
            throw new CNotOwnerException();

        return post.updatePost(postDto.getTitle(), postDto.getContent());
    }

    public boolean deletePost(long postId, String email){
        Post post = getPost(postId);
        User user = post.getUser();

        if(!email.equals(user.getEmail()))
            throw new CNotOwnerException();

        postRepository.delete(post);
        return true;

    }

    // 검색
//    public List<Post> searchPost(String boardName, String type, String keword){
//        List<Post> list = null;
//        Board board = boardRepository.findByName(boardName);
//        switch (type){
//            case "all":
//                list = postRepository.findByBoardAndPostsByTitleContainingOrContentContaining(board, keword);
//                break;
//            case "author":
//                list = postRepository.findByBoardAndPostsByAuthorContaining(board, keword);
//                break;
//            case "title":
//                list = postRepository.findByBoardAndPostsByTitleContaining(board, keword);
//                break;
//            case "content":
//                list = postRepository.findByBoardAndPostsByContentContaining(board, keword);
//                break;
//            default:
//                throw new CResourceNotExistException();
//        }
//        return list;
//    }

}