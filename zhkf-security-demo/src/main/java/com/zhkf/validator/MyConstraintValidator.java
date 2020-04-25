package com.zhkf.validator;

import com.zhkf.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MyConstraintValidator implements ConstraintValidator<MyConstraint,Object> {

    @Autowired
    private HelloService helloService;
    @Override
    public void initialize(MyConstraint myConstraint) {     //校验器初始化
            System.out.println("校验初始化...");
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {       //校验逻辑
     helloService.greeting("tom");
       System.out.println(value);
        return false;
    }
}
