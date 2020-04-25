package com.zhkf.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.FIELD})     //自定义标注在方法与字段上面
@Retention(RetentionPolicy.RUNTIME)     //运行时的注解
@Constraint(validatedBy = MyConstraintValidator.class)           //校验逻辑注解
public @interface MyConstraint {
    String message() default "{org.hibernate.validator.constraints.NotBlank.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
