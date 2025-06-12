package com.shippingsystem.exception;

import lombok.Getter;

/**
 * 业务异常
 */
@Getter
public class BusinessException extends RuntimeException {
    
    /**
     * 错误码
     */
    private final Integer code;
    
    /**
     * 错误消息
     */
    private final String message;
    
    public BusinessException(String message) {
        super(message);
        this.code = 500;
        this.message = message;
    }
    
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
    
    public BusinessException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }
    
    /**
     * 参数错误
     */
    public static BusinessException badRequest(String message) {
        return new BusinessException(400, message);
    }
    
    /**
     * 未授权
     */
    public static BusinessException unauthorized(String message) {
        return new BusinessException(401, message);
    }
    
    /**
     * 禁止访问
     */
    public static BusinessException forbidden(String message) {
        return new BusinessException(403, message);
    }
    
    /**
     * 资源未找到
     */
    public static BusinessException notFound(String message) {
        return new BusinessException(404, message);
    }
    
    /**
     * 服务器内部错误
     */
    public static BusinessException internalError(String message) {
        return new BusinessException(500, message);
    }
} 