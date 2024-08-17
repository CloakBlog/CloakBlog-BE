package com.diev.blog.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriesRepository extends JpaRepository<Categories, Integer> {
    List<Categories> findAll();

    Categories findByName(String categoryName);
}
