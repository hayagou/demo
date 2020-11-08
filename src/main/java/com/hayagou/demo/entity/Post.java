package com.hayagou.demo.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Proxy(lazy = false)
public class Post extends Time{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false, length = 100)
    private String author;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 500)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Join 테이블이 Json결과에 표시되지 않도록 처리.
    protected Board getBoard(){
        return board;
    }

    public Post(String title, String content, Board board, User user) {
        this.author = user.getName();
        this.title = title;
        this.content = content;
        this.board = board;
        this.user = user;
    }

    public Post updatePost(String title, String content){
        this.title = title;
        this.content = content;
        return this;
    }

}