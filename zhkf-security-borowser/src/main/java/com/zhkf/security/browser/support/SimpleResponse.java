package com.zhkf.security.browser.support;

/**
 * 方法返回类型处理
 */
public class SimpleResponse {

    public SimpleResponse(Object content){
        this.content=content;
    }
    private Object content;

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
