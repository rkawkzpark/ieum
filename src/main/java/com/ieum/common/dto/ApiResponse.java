package com.ieum.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private final boolean success;

    @JsonInclude(JsonInclude.Include.NON_NULL) // data가 null이면 json에 포함하지 않음
    private final T data;

    @JsonInclude(JsonInclude.Include.NON_NULL) // error가 null이면 json에 포함하지 않음
    private final ErrorResponse error;

    // 성공 시
    private ApiResponse(boolean success, T data) {
        this.success = success;
        this.data = data;
        this.error = null;
    }

    // 실패 시
    private ApiResponse(boolean success, ErrorResponse error) {
        this.success = success;
        this.data = null;
        this.error = error;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data);
    }

    public static ApiResponse<?> error(ErrorResponse error) {
        return new ApiResponse<>(false, error);
    }
}