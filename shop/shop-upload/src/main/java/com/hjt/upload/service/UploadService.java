package com.hjt.upload.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2020/12/3 11:12
 */
public interface UploadService {

    public String uploadImage(MultipartFile file);
}

