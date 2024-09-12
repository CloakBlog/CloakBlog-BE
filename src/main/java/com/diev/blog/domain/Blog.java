package com.diev.blog.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@Table
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "host_member_id")
    private Member hostMember;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "blog_title", nullable = false)
    private String blogTitle;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @OneToMany(mappedBy = "blog")
    private Set<BlogMember> blogMembers;

    public Blog(Member hostMember, String code, String blogTitle) {
        this.hostMember = hostMember;
        this.code = code;
        this.blogTitle = blogTitle;
    }
}
