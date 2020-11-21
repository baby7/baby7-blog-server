package com.baby7blog.modules.file.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    // 文件上传
    String uploadFile(MultipartFile file);
}
