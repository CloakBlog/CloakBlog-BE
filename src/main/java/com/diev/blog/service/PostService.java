package com.diev.blog.service;

import com.diev.blog.domain.Categories;

import com.diev.blog.domain.Post;
import com.diev.blog.dto.BlogDto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface PostService {

    // Create
    @Transactional
    Post save(String title, String content, String img, String folderPath, Set<Categories> categories);

    // Read - All
    Page<Post> findAll(int page, int size);

    // Read - Detail
    Post getById(Long id);

    // Update
    @Transactional
    Post update(long id, BlogDto request) throws IOException;

    // Delete
    @Transactional
    void delete(long id);

    // Categories
    List<Categories> findAllCategories();

    Page<Post> findByCategoryName(String name, Pageable pageable);

    // Search
    Page<Post> findByTitleContainingOrContextContaining(String query, Pageable pageable);
}
