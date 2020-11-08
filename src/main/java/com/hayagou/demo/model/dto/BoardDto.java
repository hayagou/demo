package com.hayagou.demo.model.dto;

import com.hayagou.demo.entity.Board;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {

    @ApiModelProperty(value = "게시판 이름", required = true)
    private String boardName;

    public Board toEntity(){
        return Board.builder().name(this.boardName).build();
    }

}