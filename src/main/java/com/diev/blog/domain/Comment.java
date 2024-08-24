package com.diev.blog.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Entity
@Table
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    @ManyToOne
    @JoinColumn(name = "writerEmail")
    private Member writer;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

    private String content;
}

