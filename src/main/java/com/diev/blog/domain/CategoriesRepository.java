package com.diev.blog.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface CategoriesRepository extends JpaRepository<Categories, Integer> {
    List<Categories> findAll();

    Categories findByName(String categoryName);

    Set<Categories> findByIdIn(Set<Integer> categoryIds);
}
