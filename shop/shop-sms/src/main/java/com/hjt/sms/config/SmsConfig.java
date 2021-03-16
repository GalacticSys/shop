package com.hjt.sms.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2021/3/15 0:26
 */
@ConfigurationProperties(prefix = "shop.sms")
public class SmsConfig {

    String accessKeyId;
    String accessKeySecret;
    String signName;
    String verifyCodeTemplate;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getVerifyCodeTemplate() {
        return verifyCodeTemplate;
    }

    public void setVerifyCodeTemplate(String verifyCodeTemplate) {
        this.verifyCodeTemplate = verifyCodeTemplate;
    }
}
