package com.aprilstore.util.sms;

import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wd on 2017/10/13.
 */
public abstract class SMSRequest extends AliyunServiceRequest {

    private String phoneNumbers;
    private String signName;
    private String templateCode;
    private String outId;
    private String regionId;

    protected Map<String, String> templateParam = new HashMap<>();

    @Override
    public void initalParam() {
        super.initalParam();
        if (!StringUtils.isEmpty(phoneNumbers)) {
            param.put("PhoneNumbers", phoneNumbers);
        }
        if (!StringUtils.isEmpty(signName)) {
            param.put("SignName", signName);
        }
        if (!StringUtils.isEmpty(templateCode)) {
            param.put("TemplateCode", templateCode);
        }
        if (!StringUtils.isEmpty(outId)) {
            param.put("OutId", outId);
        }
        param.put("TemplateParam", JSON.toJSONString(templateParam));
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(String phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }
}

