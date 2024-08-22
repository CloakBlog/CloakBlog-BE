package com.diev.blog.service;

import com.diev.blog.domain.Categories;
import com.diev.blog.domain.Blog;

import com.diev.blog.dto.BlogDto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface DiaTwoService {

    // Create
    @Transactional
    Blog saveDiaTwoBlog(String title, String content, String img, String folderPath, Set<Categories> categories);

    // Read - All
    Page<Blog> getAllDiaTwoBlog(int page, int size);

    // Read - Detail
    Blog getById(Long id);

    // Update
    @Transactional
    Blog updateDiaTwoBlog(long id, BlogDto request) throws IOException;

    // Delete
    @Transactional
    void delete(long id);

    // Categories
    List<Categories> getAllCategories();

    Page<Blog> findByCategoryName(String name, Pageable pageable);

    // Search
    Page<Blog> findByTitleContainingOrContextContaining(String query, Pageable pageable);
}
