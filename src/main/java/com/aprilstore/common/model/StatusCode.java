package com.aprilstore.common.model;

import com.google.common.collect.ImmutableMap;

/**
 * @author wd
 */
public enum StatusCode implements RestStatus {

    OK(200, "请求成功"),
    /**
     * 系统错误
     */
    SYSTEM_ERROR(999, "系统错误"),

    // 40xxx 客户端不合法的请求
    INVALID_MODEL_FIELDS(40001, "字段校验非法"),

    /**
     * 参数类型非法，常见于SpringMVC中String无法找到对应的enum而抛出的异常
     */
    INVALID_PARAMS_CONVERSION(40002, "参数类型非法"),
    /**
     * 权限错误
     */
    AUTHORITY_ERROR(40003, "数据权限错误"),

    /**
     * 数据不存在
     */
    DATA_NOT_EXIST(40004, "数据不存在"),
	
	 /**
     * token失效
     */
    TOKEN_ERROR(40005, "token失效");

    private final int code;

    private final String message;

    private static final ImmutableMap<Integer, StatusCode> CACHE;

    static {
        final ImmutableMap.Builder<Integer, StatusCode> builder = ImmutableMap.builder();
        for (StatusCode statusCode : values()) {
            builder.put(statusCode.code(), statusCode);
        }
        CACHE = builder.build();
    }

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static StatusCode valueOfCode(int code) {
        final StatusCode status = CACHE.get(code);
        if (status == null) {
            throw new IllegalArgumentException("No matching constant for [" + code + "]");
        }
        return status;
    }

    public static StatusCode valueOf(int code) {
        return CACHE.get(code);
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

}
