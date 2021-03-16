package com.hjt.sms.listener;

import com.hjt.sms.Utils.SmsUtils;
import com.hjt.sms.config.SmsConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2021/3/15 0:39
 */
@Component
public class SmsListener {
    @Autowired
    private SmsUtils smsUtils;

    @Autowired
    private SmsConfig smsConfig;


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "SHOP.SMS.QUEUE",durable = "true"),
            exchange = @Exchange(value = "SHOP.SMS.EXCHANGE",ignoreDeclarationExceptions = "true",type = ExchangeTypes.TOPIC),
            key = {"verifyCode.sms"}
    ))
    public void SendSms(Map<String,String> map){
        if (CollectionUtils.isEmpty(map)){
            return;
        }
        String phone = map.get("phone");
        String code = map.get("code");
        String jsonParam = "{\"code\":\""+code+"\"}" ;
        if (StringUtils.isNotBlank(phone)&&StringUtils.isNotBlank(code)){
            smsUtils.sendSms(phone,code,smsConfig.getSignName(),smsConfig.getVerifyCodeTemplate(),jsonParam);
        }
    }
}
