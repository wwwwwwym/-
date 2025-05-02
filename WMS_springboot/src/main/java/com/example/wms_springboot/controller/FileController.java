package com.example.wms_springboot.controller;

import cn.hutool.core.io.FileUtil;
import com.example.wms_springboot.config.AuthAccess;
import com.example.wms_springboot.utils.ResponseResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;



@CrossOrigin
@RestController
@RequestMapping("/file")
public class FileController {
    private static final String ROOT_PATH =System.getProperty("user.dir") + File.separator + "files";

    @AuthAccess
    @PostMapping("/upload")
    public ResponseResult upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();//文件原始名称
        String mainName = FileUtil.mainName(originalFilename);
        String extName = FileUtil.extName(originalFilename);

        if(!FileUtil.exist(ROOT_PATH)){
            FileUtil.mkdir(ROOT_PATH);//不存在就创建
        }
        if(FileUtil.exist(ROOT_PATH + File.separator + originalFilename)){//当前上传的已经存在，就要重命名
            originalFilename = mainName + "_" + System.currentTimeMillis() + "." + extName;
        }
        File saveFile = new File(ROOT_PATH + File.separator + originalFilename);
        System.out.println("文件保存路径：" + saveFile.getAbsolutePath());
        file.transferTo(saveFile);
        String url= "http://localhost:9000/file/download/" + originalFilename;

        return ResponseResult.success(url);//返回文件链接，就是文件下载地址

    }


    @AuthAccess
    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {

        String filePath = ROOT_PATH + File.separator + fileName;
        if(!FileUtil.exist(filePath)){
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
             return;
        }

        byte[] bytes = FileUtil.readBytes(filePath);
        System.out.println("尝试下载文件路径：" + filePath);
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            outputStream.write(bytes);
            outputStream.flush();
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error reading or writing file");
            e.printStackTrace(); // 打印栈跟踪信息，帮助调试
        }


//        byte[] bytes = FileUtil.readBytes(filePath);
//        ServletOutputStream outputStream = response.getOutputStream();
//        outputStream.write(bytes);//数组是一个字节数组
//        outputStream.flush();
//        outputStream.close();

    }
}


