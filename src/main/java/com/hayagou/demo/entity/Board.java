package com.hayagou.demo.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Builder
    public Board(String name) {
        this.name = name;
    }

    public Board updateBaordName(String boardName){
        this.name = boardName;
        return this;
    }
}