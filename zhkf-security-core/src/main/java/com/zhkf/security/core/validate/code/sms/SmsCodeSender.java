package com.zhkf.security.core.validate.code.sms;

public interface SmsCodeSender {
    void send(String mobile,String code);       //向那个手机发送，短信验证码是什么
}
