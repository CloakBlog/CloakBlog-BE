package com.diev.blog.controller;

import com.diev.blog.domain.*;
import com.diev.blog.dto.PostDto;
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
@RequestMapping("/post")
public class PostController {

    @Autowired
    private final PostService postService;

    @Autowired
    ServletContext context;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String postHome(Model model,
                          @RequestParam(value = "page", defaultValue = "0") int page,
                          @RequestParam(value = "size", defaultValue = "5") int size) {
        Page<Post> postPage = postService.findAll(page, size);
        model.addAttribute("posts", postPage.getContent());
        model.addAttribute("totalPages", postPage.getTotalPages());
        model.addAttribute("currentPage", page);
        return "/post/home";
    }

    @GetMapping("/category/{name}")
    public String getPostsByCategory(@PathVariable("name") String name,
                                     @RequestParam(value = "page", defaultValue = "0") int page,
                                     Model model) {
        Pageable pageable = PageRequest.of(page, 5); // 페이지 크기는 5로 설정
        Page<Post> postsPage = postService.findByCategoryName(name, pageable);

        model.addAttribute("posts", postsPage);
        model.addAttribute("category", name);
        return "/post/home";
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
        return "/post/home";
    }

    @GetMapping("/post/{id}")
    public String findAllPosts(@PathVariable("id") Long id, Model model) {
        Post post = postService.getById(id);
        model.addAttribute("post", post);
        return "/post/post_detail";
    }

    @GetMapping("/post")
    public String postWrite(Model model) {
        List<Categories> categories = postService.findAllCategories();
        model.addAttribute("categories", categories);
        return "/post/post_create";
    }

    // 수정 예정

    @Autowired
    CategoriesRepository categoriesRepository;

    @PostMapping("/post")
    public String postSave(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("img") MultipartFile img,
            @RequestParam("categoryIds") Set<Integer> categoryIds
            ) throws IOException {



//        Member writer = memberRepository.findById(writerId).orElse(null);
        Set<Categories> categories = categoriesRepository.findByIdIn(categoryIds);
//
        PostDto postDto = new PostDto(title, content, img, categories, "test");
        System.out.println(title);
        System.out.println(content);
        System.out.println(img);
        System.out.println(categoryIds);
//        @RequestParam("writerId") Long writerId
//        System.out.println(writerId);


        MultipartFile multipartFile = postDto.getImg();

        // 파일이 비어 있는지 확인
        if (multipartFile.isEmpty()) {
            // 파일이 첨부되지 않은 경우의 처리
            // 예를 들어, 기본 이미지를 설정하거나 파일 업로드 없이 저장할 수 있습니다.
            Post post = postService.save(postDto, null, null);
            return "redirect:/post/post/" + post.getId();
        }

        String basePath = "/Users/minseongcheol/Documents/dev/diev/blog/src/main/resources/uploads";
        String appName = "testPost";

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

        Post post = postService.save(postDto, uniqueFileName, folderPath);
        return "redirect:/post/post/" + post.getId();
    }


    @PutMapping(value = "/post/{id}", consumes = "multipart/form-data")
    public Post postUpdate(@PathVariable("id") long id, @ModelAttribute PostDto request) throws IOException {
        return postService.update(id, request);
    }

    @DeleteMapping("/post/{id}")
    public String postDelete(@PathVariable("id") long id) {
        postService.delete(id);
        return "Delete Successfully!";
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
