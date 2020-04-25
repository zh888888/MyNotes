package com.zhkf.security.core.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 *
 */
public class ValidateCodeException extends AuthenticationException {

    private static final long serialVersionUID = -2224983836637642756L;

    public ValidateCodeException(String msg){
        super(msg);
    }
}
