package com.diev.blog.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    Page<Blog> findAll(Pageable pageable);

    Page<Blog> findByCategoriesContains(Categories categories, Pageable pageable);

    Page<Blog> findByTitleContainingOrContextContaining(String title, String content, Pageable pageable);
}
