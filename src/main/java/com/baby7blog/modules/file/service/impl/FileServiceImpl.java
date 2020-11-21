package com.baby7blog.modules.file.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baby7blog.config.WebConfig;
import com.baby7blog.modules.blog.service.SettingService;
import com.baby7blog.modules.file.local.FileUploadUtils;
import com.baby7blog.modules.file.oss.KeyUtil;
import com.baby7blog.modules.file.service.FileService;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    SettingService settingService;

    // 文件上传
    @Override
    public String uploadFile(MultipartFile file) {
        JSONObject setting = settingService.getSetting();
        JSONObject uploadFile = setting.getJSONObject("uploadFile");
        String imageUrl = "";
        if(uploadFile.getJSONObject("local").getBoolean("state")){
            //本地服务上传
            imageUrl = localUpload(file);
        }
        else if(uploadFile.getJSONObject("qiniu").getBoolean("state")){
            //七牛云存储上传
            imageUrl = qiniuUpload(file, uploadFile.getJSONObject("qiniu"));
        }
        return imageUrl;
    }

    // 本地上传
    private String localUpload(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            fileName = Objects.nonNull(fileName) ? fileName : "";
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            String newFileName = FileUploadUtils.upload(WebConfig.getProfile(), file, "");
            return "/file/info/get/" + newFileName + suffix;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    // 七牛云存储上传
    private String qiniuUpload(MultipartFile file, JSONObject setting) {
        String accessKey = setting.getString("accessKey");
        String secretKey = setting.getString("secretKey");
        String bucket = setting.getString("bucket");
        String path = setting.getString("path");
        String local = setting.getString("local");
        try {
            InputStream inputStream = file.getInputStream();
            // 构造一个带指定Zone对象的配置类
            Configuration cfg = new Configuration(getQiniuRegion(local));
            // 其他参数参考类注释
            UploadManager uploadManager = new UploadManager(cfg);
            // 生成上传凭证，然后准备上传
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(inputStream, KeyUtil.genUniqueKey(), upToken, null, null);
                // 解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                return path + "/" + putRet.key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // 获取七牛云区域
    private Region getQiniuRegion(String local) {
        switch (local) {
            // 华东
            case "huadong": return Region.huadong();
            // 华北
            case "huabei": return Region.huabei();
            // 华南
            case "huanan": return Region.huanan();
            // 北美
            case "beimei": return Region.beimei();
            // 新加坡
            case "xinjiapo": return Region.xinjiapo();
        }
        return null;
    }
}
