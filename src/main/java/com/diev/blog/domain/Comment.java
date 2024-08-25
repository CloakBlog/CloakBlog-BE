package com.diev.blog.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    @ManyToOne
    @JoinColumn(name = "writer")
    private Member writer;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

    private String content;
}

