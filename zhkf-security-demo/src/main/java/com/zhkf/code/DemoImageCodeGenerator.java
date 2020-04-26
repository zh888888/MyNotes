package com.zhkf.code;

import com.zhkf.security.core.validate.code.ImageCode;
import com.zhkf.security.core.validate.code.ValidateCode;
import com.zhkf.security.core.validate.code.ValidateCodeGenerator;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

//@Component("imageCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator {


    @Override
    public ValidateCode createImageCode(ServletWebRequest request) {
        System.out.println("更高级的图形验证码生成代码");
        return null;
    }
}
