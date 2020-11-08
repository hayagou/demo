package com.hayagou.demo.controller;


import com.hayagou.demo.model.response.CommonResult;
import com.hayagou.demo.model.response.SingleResult;
import com.hayagou.demo.service.ResponseService;
import com.hayagou.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"1. Sign"})
@RequiredArgsConstructor
@RestController
public class SignController {

    private final UserService userService;
    private final ResponseService responseService;


    @ApiOperation(value = "로그인", notes = "회원 로그인을 한다.")
    @PostMapping(value = "/signin")
    public SingleResult<String> signIn(@ApiParam(value = "회원 이메일", required = true) @RequestParam String email,
                                       @ApiParam(value = "비밀 번호", required = true) @RequestParam String password){

        return responseService.getSingleResult(userService.signIn(email, password));
    }

    @ApiOperation(value = "회원 가입", notes = "회원 가입을 한다.")
    @PostMapping(value = "/signup")
    public CommonResult signIn(@ApiParam(value = "회원 이메일", required = true) @RequestParam String email,
                               @ApiParam(value = "비밀번호", required = true) @RequestParam String password,
                               @ApiParam(value = "이름", required = true) @RequestParam String name){
        userService.signUp(email, password, name);
        return responseService.getSuccessResult();
    }
}