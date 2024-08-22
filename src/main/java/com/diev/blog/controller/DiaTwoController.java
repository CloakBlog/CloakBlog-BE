package com.diev.blog.controller;

import com.diev.blog.domain.Categories;
import com.diev.blog.domain.Blog;
import com.diev.blog.dto.BlogDto;
import com.diev.blog.service.BlogService;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.List;
import java.util.Set;

import com.diev.blog.utils.FileUtils;

//@RestController
@Controller
@RequestMapping("/diatwo")
public class DiaTwoController {

    @Autowired
    private final BlogService blogService;

    @Autowired
    ServletContext context;

    public DiaTwoController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/")
    public String diaHome(Model model,
                          @RequestParam(value = "page", defaultValue = "0") int page,
                          @RequestParam(value = "size", defaultValue = "5") int size) {
        Page<Blog> blogPage = blogService.findAll(page, size);
        model.addAttribute("posts", blogPage.getContent());
        model.addAttribute("totalPages", blogPage.getTotalPages());
        model.addAttribute("currentPage", page);
        return "/dia-two/home";
    }

    @GetMapping("/category/{name}")
    public String getPostsByCategory(@PathVariable("name") String name, @RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 5); // 페이지 크기는 5로 설정
        Page<Blog> postsPage = blogService.findByCategoryName(name, pageable);

        model.addAttribute("posts", postsPage);
        model.addAttribute("category", name);
        return "/dia-two/home";
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, @RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Blog> searchResults = blogService.findByTitleContainingOrContextContaining(query, pageable);
        model.addAttribute("posts", searchResults.getContent());
        model.addAttribute("totalPages", searchResults.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("query", query);
        return "/dia-two/home";
    }

    @GetMapping("/post/{id}")
    public String getAllBlogs(@PathVariable("id") Long id, Model model) {
        Blog blog = blogService.getById(id);
        model.addAttribute("post", blog);
        return "/dia-two/post_detail";
    }

    @GetMapping("/post")
    public String diaBlogWrite(Model model) {
        List<Categories> categories = blogService.findAllCategories();
        model.addAttribute("categories", categories);
        return "/dia-two/post_create";
    }

    @PostMapping("/post")
    public String diaBlogSave(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("img") MultipartFile multipartFile,
            @RequestParam("categoryIds") Set<Categories> categories) throws IOException {

        // 파일이 비어 있는지 확인
        if (multipartFile.isEmpty()) {
            // 파일이 첨부되지 않은 경우의 처리
            // 예를 들어, 기본 이미지를 설정하거나 파일 업로드 없이 저장할 수 있습니다.
            Blog blog = blogService.save(title, content, null, null, categories);
            return "redirect:/diatwo/post/" + blog.getId();
        }

        String basePath = "/Users/minseongcheol/Documents/dev/diev/blog/src/main/resources/uploads";
        String appName = "Dia-Two";

        // 폴더 경로 생성 및 유니크 파일명 생성
        String folderPath = FileUtils.generateFolderPathByAppAndDate(basePath, appName);
        String uniqueFileName = FileUtils.generateUniqueFileName(multipartFile.getOriginalFilename());
        String fullPath = folderPath + uniqueFileName;
        File saveFile = new File(fullPath);

        // 이미지 압축 및 저장
        FileUtils.saveCompressedImage(multipartFile, saveFile.getAbsolutePath());
        try {
            // void transferTo(File dest) throws IOException 업로드한 파일 데이터를 지정한 파일에 저장
            multipartFile.transferTo(saveFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Blog blog = blogService.save(title, content, uniqueFileName, folderPath, categories);
        return "redirect:/diatwo/post/" + blog.getId();
    }


    @PutMapping(value = "/post/{id}", consumes = "multipart/form-data")
    public Blog diaBlogUpdate(@PathVariable("id") long id, @ModelAttribute BlogDto request) throws IOException {
        return blogService.update(id, request);
    }

    @DeleteMapping("/post/{id}")
    public String diaBlogDelete(@PathVariable("id") long id) {
        blogService.delete(id);
        return "Delete Successfully!";
    }

    @GetMapping("/img")
    public ResponseEntity<Resource> display(@RequestParam("filename") String filename) {
        String path = "/Users/minseongcheol/Documents/dev/diev/blog/src/main/resources/uploads";
        String appName = "/Dia-Two/";
        String fullPath = path + appName + filename;
        Resource resource = new FileSystemResource(fullPath);

        if (!resource.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders header = new HttpHeaders();
        Path filePath = null;
        try{
            filePath = Paths.get(fullPath);
            header.add("Content-type", Files.probeContentType(filePath));
        }catch(IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(resource, header, HttpStatus.OK);
    }
}
