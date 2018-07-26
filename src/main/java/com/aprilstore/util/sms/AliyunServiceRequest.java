package com.aprilstore.util.sms;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wd on 2017/10/16.
 */
public class AliyunServiceRequest {
    protected Map<String, String> param = new HashMap<>();

    public Map<String, String> getParam() {
        initalParam();
        return param;
    }

    public void initalParam() {
    }
}
