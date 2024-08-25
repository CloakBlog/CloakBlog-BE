package com.diev.blog.dto;

import com.diev.blog.domain.Categories;
import com.diev.blog.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostDto {
    private String title;
    private String content;
    private MultipartFile img;
    private Set<Categories> categoryIds;
    private String writer;
}
