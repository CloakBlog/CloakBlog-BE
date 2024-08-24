package com.diev.blog.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@NoArgsConstructor
@Entity
@Table
public class User {

    @Id
    @Column(name = "user_id", length = 100, nullable = false)
    private String userId;

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

    public User(String userId, String password, String name, String nickName, String code) {
        this.userId = userId;
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
