package com.zhkf.web.controller;

import com.zhkf.dto.FileInfo;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

@RestController
@RequestMapping("/file")
public class FileController {

    private  String folder = "G:\\yjxm\\zhkf-security-demo\\src\\main\\java\\com\\zhkf\\web\\controller";

    @PostMapping
    public FileInfo upload(MultipartFile file) throws Exception {
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());

        File localFile = new File(folder, new Date().getTime() + ".txt");
        //file.getInputStream();    //将文件发送到第三方存储里面
        file.transferTo(localFile);
        return new FileInfo(localFile.getAbsolutePath());
    }

    @GetMapping("/{id}")
    public void download(@PathVariable String id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws  Exception{

        try(
                InputStream inputStream = new FileInputStream(new File(folder,id+".txt"));
                OutputStream outputStream  = httpServletResponse.getOutputStream();
                ){
        httpServletResponse.setContentType("application/x-download");
        httpServletResponse.addHeader("Content-Disposition","attachment;filename=test.txt");
            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();
        }
    }

}