package com.diev.blog.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import java.sql.Date;

@Entity
@Table
public class BlogMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private Date createdAt;
}

