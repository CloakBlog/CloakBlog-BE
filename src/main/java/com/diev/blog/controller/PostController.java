package com.diev.blog.controller;

import com.diev.blog.domain.*;
import com.diev.blog.service.PostService;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private final PostService postService;

    @Autowired
    ServletContext context;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String query,
                         @RequestParam(value = "page", defaultValue = "0") int page,
                         Model model) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Post> searchResults = postService.findByTitleContainingOrContextContaining(query, pageable);
        model.addAttribute("posts", searchResults.getContent());
        model.addAttribute("totalPages", searchResults.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("query", query);
        return "post/home";
    }

    @GetMapping("/img")
    public ResponseEntity<Resource> display(@RequestParam("filename") String filename) {
        String path = "/Users/minseongcheol/Documents/dev/diev/blog/src/main/resources/uploads";
        String appName = "/testPost/";
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
