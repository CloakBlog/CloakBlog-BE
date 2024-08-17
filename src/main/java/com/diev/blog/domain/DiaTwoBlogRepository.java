package com.diev.blog.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaTwoBlogRepository extends JpaRepository<DiaTwoBlog, Long> {
    Page<DiaTwoBlog> findAll(Pageable pageable);

    Page<DiaTwoBlog> findByCategoriesContains(Categories categories, Pageable pageable);
}
