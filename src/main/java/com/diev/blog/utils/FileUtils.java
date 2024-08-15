package com.diev.blog.utils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
    public static String generateUniqueFileName(String originalFileName) {
        String uuid = UUID.randomUUID().toString();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return uuid + extension;
    }

    public static String generateFolderPathByAppAndDate(String basePath, String appName) {
        String folderPath = basePath + "/" + appName + "/";
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folderPath;
    }

    public static void saveCompressedImage(MultipartFile inputFile, String savePath) throws IOException {
        Thumbnails.of(inputFile.getInputStream())
                .size(1920, 1080)
                .outputQuality(0.75)
                .toFile(new File(savePath));
    }
}
