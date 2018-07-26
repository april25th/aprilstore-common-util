package com.aprilstore.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author yunfei.li
 *         类描述: TODO
 *         日期： 2018/6/11
 */
@ApiModel("基础请求实体")
public class RequestBean implements Serializable {

    private static final long serialVersionUID = -7443304902819898146L;

    @ApiModelProperty("requestId")
    private String requestId;

    @ApiModelProperty("token")
    private String token;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
