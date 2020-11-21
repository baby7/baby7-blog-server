package com.baby7blog.modules.file.controller;

import com.baby7blog.config.WebConfig;
import com.baby7blog.modules.blog.vo.R;
import com.baby7blog.modules.file.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@Slf4j
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileService fileService;

    /**
     * 保存头像
     */
    @PostMapping("/change/save")
    public R save(@RequestParam("file") MultipartFile file) {
        return R.ok(fileService.uploadFile(file));
    }

    /**
     * 获取头像文件
     */
    @GetMapping(value = "/info/get/{url}")
    public void get(@PathVariable String url, HttpServletResponse response) throws IOException {
        OutputStream os = null;
        try {
            url = url.substring(0, url.lastIndexOf("."));
            BufferedImage image = ImageIO.read(new FileInputStream(new File(WebConfig.getProfile() + url)));
            response.setContentType("image/jpg");
            os = response.getOutputStream();
            if (image != null) {
//                ImageIO.write(image, "jpg", os);
                ImageIO.write(image, "png", os);
            }
        } catch (IOException e) {
            log.error("获取图片异常{}",e.getMessage());
        } finally {
            if (os != null) {
                os.flush();
                os.close();
            }
        }
    }
}
