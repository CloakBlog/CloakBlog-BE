package com.diev.blog.domain;

import com.diev.blog.dto.BlogDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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

    public DiaTwoBlog(BlogDto request) {
        this.title = request.getTitle();
        this.context = request.getContext();
        this.img = request.getImg();
    }

    public void update(BlogDto request) {
        this.title = request.getTitle();
        this.context = request.getContext();
        this.img = request.getImg();
    }
}
