package com.diev.blog.domain;

import com.diev.blog.dto.PostDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;


@Table
@Entity
@Getter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "blogId")
    private Blog blog;

    @ManyToOne
    @JoinColumn(name = "writer")
    private Member writer;

    @Column(name = "title", nullable = false, length = 20)
    private String title;

    @Column(name = "context")
    private String context;

    @Column(name = "img")
    private String img;

    @Column(name = "folderPath")
    private String folderPath;

    @ManyToMany
    @JoinTable(
            name = "post_categories",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Categories> categories;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Post(PostDto postDto, String uniqueFileName, String folderPath) {
        this.title = postDto.getTitle();
//        this.writer = postDto.getWriter();
        this.context = postDto.getContent();
        this.img = uniqueFileName;
        this.folderPath = folderPath;
//        this.categories = postDto.getCategoryIds();
    }

    public void update(String title, String content, String img, String folderPath, Set<Categories> categories) {
        this.title = title;
        this.context = content;
        this.img = img;
        this.folderPath = folderPath;
        this.categories = categories;
    }
}
