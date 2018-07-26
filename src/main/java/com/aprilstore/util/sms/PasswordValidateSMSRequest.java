package com.aprilstore.util.sms;

/**
 * Created by wd on 2017/10/16.
 */
public class PasswordValidateSMSRequest extends SMSRequest {
    private String validateCode;

    public PasswordValidateSMSRequest()
    {
        this.setSignName("真学大数据");
    }

    @Override
    public void initalParam() {
        super.initalParam();
        templateParam.put("code",validateCode);
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }
}
