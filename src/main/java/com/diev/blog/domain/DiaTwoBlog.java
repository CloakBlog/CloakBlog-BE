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

    @Column(name="context", nullable = true)
    private String context;

    @Column(name="img", nullable = true)
    private byte[] img;

    @Column(name="created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public DiaTwoBlog(String title, String content, byte[] img) {
        this.title = title;
        this.context = content;
        this.img = img;
    }

    public void update(String title, String content, byte[] img) {
        this.title = title;
        this.context = content;
        this.img = img;
    }
}
