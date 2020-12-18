package com.baby7blog.util.file;

import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

/**
 * @author ramostear|谭朝红
 * @create-time 2019/3/20 0020-0:00
 * @modify by :
 * @since:
 */
public class Base64Converter {

    public static MultipartFile converter(String source){
        String [] charArray = source.split(",");
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = new byte[0];
        bytes = decoder.decode(charArray[1]);
        for (int i=0;i<bytes.length;i++){
            if(bytes[i]<0){
                bytes[i]+=256;
            }
        }
        return Base64Decoder.multipartFile(bytes,charArray[0]);
    }
}