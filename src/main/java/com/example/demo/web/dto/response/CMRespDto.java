package com.example.demo.web.dto.response;

import lombok.Builder;
import lombok.Getter;

// 공통 resp
@Getter
public class CMRespDto<T> {
    private Integer code; // 1 성공, -1 실패
    private String msg;
    private T body;

    @Builder
    public CMRespDto(Integer code, String msg, T body) {
        this.code = code;
        this.msg = msg;
        this.body = body;
    }
}
