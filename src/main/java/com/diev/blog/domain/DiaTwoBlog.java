package com.diev.blog.domain;

import com.diev.blog.dto.BlogDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.IOException;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table
public class DiaTwoBlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title", nullable = false, length = 20)
    private String title;

    @Column(name="context")
    private String context;

    @Column(name="img")
    private String img;

    @Column(name="folderPath")
    private String folderPath;

    @Column(name="created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public DiaTwoBlog(String title, String content, String img, String folderPath) {
        this.title = title;
        this.context = content;
        this.img = img;
        this.folderPath = folderPath;
    }

    public void update(String title, String content, String img, String folderPath) {
        this.title = title;
        this.context = content;
        this.img = img;
        this.folderPath = folderPath;
    }
}
