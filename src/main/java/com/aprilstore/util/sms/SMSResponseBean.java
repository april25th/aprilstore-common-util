package com.aprilstore.util.sms;


import com.aprilstore.common.model.ResponseBean;

/**
 * Created by wd on 2017/10/16.
 */
public class SMSResponseBean<T> extends ResponseBean {
    private String bizId;


    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }
}
