package com.hayagou.demo.controller;


import com.hayagou.demo.entity.User;
import com.hayagou.demo.model.dto.UserDto;
import com.hayagou.demo.model.response.CommonResult;
import com.hayagou.demo.model.response.ListResult;
import com.hayagou.demo.model.response.SingleResult;
import com.hayagou.demo.service.ResponseService;
import com.hayagou.demo.service.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"2. User"})
@RequiredArgsConstructor
@RestController
public class UserController {

    private final ResponseService responseService;
    private final UserService userService;


    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 단건 조회", notes = "회원정보를 조회 한다.")
    @GetMapping(value = "/user")
    public SingleResult<User> findUser() {

        return responseService.getSingleResult(userService.getUser(userService.getAuthentication().getName()));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 리스트 조회 (관리자 권한)", notes = "모든 회원을 조회 한다.")
    @GetMapping(value = "/users")
    public ListResult<UserDto> findAllUser(){
        return responseService.getListResult(userService.getUsersList());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "비밀번호 변경", notes = "회원 비밀번호를 변경 한다.")
    @PutMapping(value = "/user")
    public CommonResult modify(
            @ApiParam(value = "변경할 비밀번호", required = true) @RequestParam String newPassword
    ){
        userService.updatePassword(userService.getAuthentication().getName(),newPassword);
        return responseService.getSuccessResult();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 삭제", notes = "userId로 회원 정보를 삭제한다.")
    @DeleteMapping(value = "/user")
    public CommonResult delete(){
        userService.deleteUser(userService.getAuthentication().getName());
        return responseService.getSuccessResult();
    }
}