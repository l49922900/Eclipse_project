package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.controller.ReservationController;
import com.example.demo.controller.ScooterController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileService {
    @Value("${upload.path}") 
    private String uploadPath;
	private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    public String saveImage(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path uploadDir = Paths.get(uploadPath);

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        Path filePath = uploadDir.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // 修改這裡：返回的路徑應該對應實際的儲存位置
        return "/" + fileName;  // 或是根據你的需求調整路徑格式
    }

    public void deleteImage(String imagePath) {
        if (imagePath != null) {
            // 從路徑中提取檔案名
            String fileName = imagePath.substring(imagePath.lastIndexOf("/") + 1);
            try {
                Path path = Paths.get(uploadPath, fileName);
                Files.deleteIfExists(path);
            } catch (IOException e) {
                log.error("刪除檔案失敗", e);
            }
        }
    }
}
