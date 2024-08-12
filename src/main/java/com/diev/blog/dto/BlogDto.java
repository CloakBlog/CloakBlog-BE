package com.diev.blog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BlogDto {
    private String title;
    private String context;
    private byte[] img;

    public BlogDto(String title, String context, byte[] img) {
        this.title = title;
        this.context = context;
        this.img = img;
    }
}
