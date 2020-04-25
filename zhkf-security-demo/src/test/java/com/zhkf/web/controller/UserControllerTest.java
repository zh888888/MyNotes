package com.zhkf.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        mockMvc= MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void whenQuerySuccess() throws Exception{
     String result=   mockMvc.perform(MockMvcRequestBuilders.get("/user") //发送的请求
                .param("username","zh")
                .param("age","20")
                .param("ageTo","60")
                .param("xxx","yyy")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())       //返回的状态码
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
             .andReturn().getResponse().getContentAsString();      //返回一个集合，集合的长度是3
        System.out.println(result) ;
    }

    @Test
    public void whenGenInfoSuccess() throws Exception{
      String result=  mockMvc.perform(MockMvcRequestBuilders.get("/user/1")
        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("tom"))
                 .andReturn().getResponse().getContentAsString();       //将服务器返回的字符串以json的格式输出

        System.out.println(result);
    }

    @Test
    public void whenGetInfoFail() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/user/a")
        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    /**
     * 编写一个创建成功后的测试场景
     */
    @Test
    public void whenCreateSuccess() throws Exception{
     Date date=new Date();
     System.out.println(date.getTime());
        String content="{\"username\":\"tom\",\"password\":null,\"brityday\":"+date.getTime()+"}";
      String result=  mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                 .content(content)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect((MockMvcResultMatchers.jsonPath("$.id").value("1")))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    //更新测试
    @Test
    public void whenUpdateSuccess() throws Exception{
        Date date = new Date(LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        System.out.println(date.getTime());
        String content="{\"id\":\"1\",\"username\":\"tom\",\"password\":null,\"brityday\":"+date.getTime()+"}";
        String result=  mockMvc.perform(MockMvcRequestBuilders.put("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect((MockMvcResultMatchers.jsonPath("$.id").value("1")))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    //删除
    @Test
    public void whenDeleteSuccess() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    //模拟文件上传
    @Test
    public void whenUploadSuccess() throws Exception{

       String result= mockMvc.perform(MockMvcRequestBuilders.fileUpload("/file")
        .file(new MockMultipartFile("file","test.exe","multipart/form-data","hello upload".getBytes("UTF-8"))))
                .andExpect(MockMvcResultMatchers.status().isOk())
               .andReturn().getResponse().getContentAsString();

       System.out.println(result);
    }
}
