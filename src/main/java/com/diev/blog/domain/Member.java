package com.diev.blog.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@Table
public class Member {

    @Id
    @Column(name = "member_id", length = 100, nullable = false)
    private String memberId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "nickname", length = 100)
    private String nickName;

    @Column(name = "code")
    private String code;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @OneToOne(mappedBy = "hostMember")
    private List<Blog> hostedBlogs;

    @OneToMany(mappedBy = "member")
    private Set<BlogMember> memberships;

    @OneToMany(mappedBy = "writer")
    private List<Post> posts;

    public Member(String memberId, String password, String name, String nickName, String code) {
        this.memberId = memberId;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.code = code;
    }

    public void User(String password, String nickName) {
        this.password = password;
        this.nickName = nickName;
    }
}
