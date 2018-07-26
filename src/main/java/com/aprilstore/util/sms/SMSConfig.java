package com.aprilstore.util.sms;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by wd on 2017/10/13.
 */
@Component
@ConfigurationProperties(prefix = "aliyunsms")
public class SMSConfig extends AliyunServiceConfig {
    private String templateCode;
    private String regionId;

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }


}
