package com.zhkf.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;

public class MockServer {

    public static void main(String[] args) {
        //通过端口连接服务
        WireMock.configureFor(9000);
        //清空之前的配置
        WireMock.removeAllMappings();

        //get请求
        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/user/1"))
                .willReturn(WireMock.aResponse()
                        //body里面写 json
                        .withBody("{\"username\":FantJ}")
                        //返回状态码
                        .withStatus(200)));

    }
}
