package com.lycmoons.service.impl;

import com.lycmoons.service.PhotoService;
import com.lycmoons.util.Convertor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoService {
    @Value("${spring.upload-path}")
    String uploadPath;

    @Value("${spring.upload-directory}")
    String uploadDirectory;

    @Value("${spring.serverip}")
    String serverIp;

    @Override
    public String storePhoto(MultipartFile[] files) throws IOException {
        List<String> photoUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            if(fileName!=null){
                File dest = new File(uploadPath, fileName);
                file.transferTo(dest); // 保存文件
                photoUrls.add(Convertor.mergePhotoUrl(serverIp,"8080", uploadDirectory, fileName));
            }
        }
        return Convertor.mergePhotoUrls(photoUrls);
    }
}
