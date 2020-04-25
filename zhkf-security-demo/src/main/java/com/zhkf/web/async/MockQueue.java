package com.zhkf.web.async;

import org.springframework.stereotype.Component;

/**
 * 模拟队列对象
 */
@Component
public class MockQueue {
    private String placeOrder;      //模拟订单下单的消息
    private String completeOrder;       //模拟订单完成的消息

    public String getPlaceOrder() {
        return placeOrder;
    }

    public void setPlaceOrder(String placeOrder) throws Exception{
        System.out.println("接到下单请求， "+placeOrder);
        Thread.sleep(1000);
        this.completeOrder=placeOrder;
        System.out.println("下单请求处理完毕，"+placeOrder);
    }

    public String getCompleteOrder() {
        return completeOrder;
    }

    public void setCompleteOrder(String completeOrder) {
        this.completeOrder = completeOrder;
    }
}
