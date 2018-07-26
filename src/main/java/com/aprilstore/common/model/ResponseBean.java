package com.aprilstore.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author yunfei.li
 *         类描述: TODO
 *         日期： 2018/6/8
 */
@ApiModel("基础响应实体")
public class ResponseBean<T> extends RestfulResponse {
    @ApiModelProperty("状态码")
    private int code;
    @ApiModelProperty("数据")
    private T data;
    @ApiModelProperty("信息")
    private String message;
    @ApiModelProperty("请求状态")
    private boolean success;
    @ApiModelProperty("请求Id")
    private String requestId;

    public ResponseBean() {
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public ResponseBean(boolean success) {
        this.success = success;
        if (this.success) {
            setCode(StatusCode.OK.code());
            setMessage(StatusCode.OK.message());
        }
    }

    public ResponseBean(StatusCode statusCode)
    {
        this.success=(StatusCode.OK.code() == statusCode.code());
        this.code=statusCode.code();
        this.message=statusCode.message();
    }

    public ResponseBean(int code) {
        this.success = (StatusCode.OK.code() == code);
        this.code = code;
        this.message = StatusCode.valueOf(code) == null ? null : StatusCode.valueOf(code).message();
    }

    public ResponseBean(int code, String message) {
        this.success = (StatusCode.OK.code() == code);
        this.code = code;
        this.message = message;
    }

    public ResponseBean(T data) {
        this(true);
        this.data = data;
    }


}
