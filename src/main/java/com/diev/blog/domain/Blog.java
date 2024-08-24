package com.diev.blog.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@Table
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "host_user_id", nullable = false)
    private String hostUserId;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "blog_title", nullable = false)
    private String blogTitle;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    public Blog(String hostUserId, String code, String blogTitle) {
        this.hostUserId = hostUserId;
        this.code = code;
        this.blogTitle = blogTitle;
    }
}
