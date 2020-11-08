package com.hayagou.demo.service;

import com.hayagou.demo.model.response.CommonResult;
import com.hayagou.demo.model.response.ListResult;
import com.hayagou.demo.model.response.SingleResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {

    public enum CommonResponse{
        SUCCESS(0, "성공하였습니다."),
        FAIL(-1, "실패하였습니다.");

        int code;
        String message;

        CommonResponse(int code, String message) {
            this.code = code;
            this.message = message;
        }
    }

    private void setSuccessResult(CommonResult result){
        result.setResult(true);
        result.setCode(CommonResponse.SUCCESS.code);
        result.setMessage(CommonResponse.SUCCESS.message);
    }

    // 단일 결과 처리
    public <T> SingleResult<T> getSingleResult(T data){
        SingleResult<T> result = new SingleResult<T>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }

    // 다중 결과 처리
    public <T> ListResult<T> getListResult(List<T> list){
        ListResult<T> result = new ListResult<T>();
        result.setList(list);
        setSuccessResult(result);
        return result;
    }

    // 성공 결과만 처리
    public CommonResult getSuccessResult(){
        CommonResult result = new CommonResult();
        setSuccessResult(result);
        return result;
    }

    // 실패 결과만 처리
    public CommonResult getFailResult(){
        CommonResult result = new CommonResult();
        result.setResult(false);
        result.setCode(CommonResponse.FAIL.code);
        result.setMessage(CommonResponse.FAIL.message);
        return result;
    }

    // 실패 결과만 처리
    public CommonResult getFailResult(int code, String message){
        CommonResult result = new CommonResult();
        result.setResult(false);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

}
