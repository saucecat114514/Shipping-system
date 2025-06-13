package com.shippingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用响应结果类
 * @param <T> 数据类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result<T> {
    
    /**
     * 响应码
     */
    private Integer code;
    
    /**
     * 响应消息
     */
    private String message;
    
    /**
     * 响应数据
     */
    private T data;
    
    /**
     * 请求是否成功
     */
    private Boolean success;
    
    /**
     * 成功响应
     * @param data 数据
     * @param <T> 数据类型
     * @return 响应结果
     */
    public static <T> Result<T> success(T data) {
        return Result.<T>builder()
                .code(200)
                .message("操作成功")
                .data(data)
                .success(true)
                .build();
    }
    
    /**
     * 成功响应（无数据）
     * @return 响应结果
     */
    public static <T> Result<T> success() {
        return success(null);
    }
    
    /**
     * 成功响应（带消息）
     * @param message 消息
     * @param data 数据
     * @param <T> 数据类型
     * @return 响应结果
     */
    public static <T> Result<T> success(String message, T data) {
        return Result.<T>builder()
                .code(200)
                .message(message)
                .data(data)
                .success(true)
                .build();
    }
    
    /**
     * 失败响应
     * @param message 错误消息
     * @param <T> 数据类型
     * @return 响应结果
     */
    public static <T> Result<T> error(String message) {
        return Result.<T>builder()
                .code(500)
                .message(message)
                .success(false)
                .build();
    }
    
    /**
     * 失败响应
     * @param code 错误码
     * @param message 错误消息
     * @param <T> 数据类型
     * @return 响应结果
     */
    public static <T> Result<T> error(Integer code, String message) {
        return Result.<T>builder()
                .code(code)
                .message(message)
                .success(false)
                .build();
    }
} 