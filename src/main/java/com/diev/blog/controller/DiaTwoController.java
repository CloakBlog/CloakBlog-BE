package com.diev.blog.controller;

import com.diev.blog.domain.DiaTwoBlog;
import com.diev.blog.dto.BlogDto;
import com.diev.blog.service.DiaTwoService;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.diev.blog.utils.FileUtils;

//@RestController
@Controller
@RequestMapping("/diatwo")
public class DiaTwoController {

    @Autowired
    private final DiaTwoService diaTwoService;

    @Autowired
    ServletContext context;

    public DiaTwoController(DiaTwoService diaTwoService) {
        this.diaTwoService = diaTwoService;
    }

    @GetMapping("/")
    public String diaHome(Model model) {
        model.addAttribute("posts", diaTwoService.getAllDiaTwoBlog());
        return "/dia-two/home";
    }

    @GetMapping("/post/{id}")
    public String getAllBlogs(@PathVariable("id") Long id, Model model) {
        DiaTwoBlog diaTwoBlog = diaTwoService.getById(id);
        model.addAttribute("post", diaTwoBlog);
        return "/dia-two/post_detail";
    }

    @GetMapping("/post")
    public String diaBlogWrite() {
        return "/dia-two/post_create";
    }

    @PostMapping("/post")
    public String diaBlogSave(@RequestParam("title") String title, @RequestParam("content") String content, @RequestParam("img") MultipartFile multipartFile) throws IOException {
        String basePath = "/Users/minseongcheol/Documents/dev/diev/blog/src/main/resources/uploads";
        String appName = "Dia-Two";

        // 폴더 경로 생성 및 유니크 파일명 생성
        String folderPath = FileUtils.generateFolderPathByAppAndDate(basePath, appName);
        String uniqueFileName = FileUtils.generateUniqueFileName(multipartFile.getOriginalFilename());
        File saveFile = new File(folderPath, uniqueFileName);

        // 이미지 압축 및 저장
        FileUtils.saveCompressedImage(multipartFile, saveFile.getAbsolutePath());
        try {
            // void transferTo(File dest) throws IOException 업로드한 파일 데이터를 지정한 파일에 저장
            multipartFile.transferTo(saveFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DiaTwoBlog diaTwoBlog = diaTwoService.saveDiaTwoBlog(title, content, uniqueFileName, folderPath);
        return "redirect:/diatwo/post/" + diaTwoBlog.getId();
    }


    @PutMapping(value = "/post/{id}", consumes = "multipart/form-data")
    public DiaTwoBlog diaBlogUpdate(@PathVariable("id") long id, @ModelAttribute BlogDto request) throws IOException {
        return diaTwoService.updateDiaTwoBlog(id, request);
    }

    @DeleteMapping("/post/{id}")
    public String diaBlogDelete(@PathVariable("id") long id) {
        diaTwoService.delete(id);
        return "Delete Successfully!";
    }

    @GetMapping("/img")
    public ResponseEntity<Resource> display(@RequestParam("filename") String filename) {
        String path = "/Users/minseongcheol/Documents/dev/diev/blog/src/main/resources/uploads/";
        Resource resource = new FileSystemResource(path + filename);
        if (!resource.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders header = new HttpHeaders();
        Path filePath = null;
        try{
            filePath = Paths.get(path + filename);
            header.add("Content-type", Files.probeContentType(filePath));
        }catch(IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(resource, header, HttpStatus.OK);
    }
}
