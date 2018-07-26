package com.aprilstore.util.sms;

import com.alibaba.fastjson.JSONObject;
import com.aprilstore.common.model.ResponseBean;
import com.aprilstore.common.model.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by wd on 2017/10/13.
 */
@Component
public class SMSService {
    private final Logger logger = LoggerFactory.getLogger(SMSService.class);

    @Autowired
    SMSConfig smsConfig;

    public ResponseBean<String> SendMessage(SMSRequest request) {

        ResponseBean<String> responseBean = new ResponseBean(StatusCode.SYSTEM_ERROR);
        try {
            request.setTemplateCode(smsConfig.getTemplateCode());
            request.setRegionId(smsConfig.getRegionId());
            String result = AliyunService.invokeService(smsConfig, request);
            if (!StringUtils.isEmpty(result)) {
                JSONObject object = JSONObject.parseObject(result);
                if ("OK".equals(object.getString("Code"))) {
                    responseBean = new ResponseBean(StatusCode.OK);
                    responseBean.setData(object.getString("BizId"));
                }
            }
        } catch (Exception e) {
            logger.error("send sms error" + e.toString());
            return new ResponseBean(StatusCode.SYSTEM_ERROR);
        }
        return responseBean;
    }


}
