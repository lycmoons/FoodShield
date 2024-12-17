package com.lycmoons.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PhotoService {
    String storePhoto(MultipartFile[] files) throws IOException;
}
