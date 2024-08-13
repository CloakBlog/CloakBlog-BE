package com.diev.blog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Getter
public class BlogDto {
    private String title;
    private String content;
    private MultipartFile img;

    public BlogDto(String title, String context, MultipartFile img) {
        this.title = title;
        this.content = context;
        this.img = img;
    }
}
