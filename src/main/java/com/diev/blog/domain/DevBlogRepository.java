package com.diev.blog.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DevBlogRepository extends JpaRepository<DevBlog, Long> {
    //
}
