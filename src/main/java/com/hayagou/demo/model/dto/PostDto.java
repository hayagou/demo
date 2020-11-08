package com.hayagou.demo.model.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

    @NotEmpty
//    @Size(min=2, max=100)
    @ApiModelProperty(value = "제목", required = true)
    private String title;

    @NotEmpty
//    @Size(min=2, max=500)
    @ApiModelProperty(value = "내용", required = true)
    private String content;
}
