package com.aprilstore.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by wd on 2018/6/12.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(
        value = {"hibernateLazyInitializer", "handler", "fieldHandler"},
        ignoreUnknown = true
)
public class RestfulResponse implements Serializable {
    private static final long serialVersionUID = -7443304902819898136L;

    public static final int DEFAULT_OK = 20000;
    @JsonProperty("code")
    @ApiModelProperty(
            value = "平台状态码",
            example = "20000",
            required = true
    )
    private int code = 20000;

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public RestfulResponse() {
    }


    protected boolean canEqual(Object other) {
        return other instanceof RestfulResponse;
    }


}
