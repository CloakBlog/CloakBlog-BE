package com.diev.blog.service;

import com.diev.blog.domain.Categories;

import com.diev.blog.domain.Post;
import com.diev.blog.dto.PostDto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface PostService {

    // Create
    @Transactional
    Post save(PostDto postDto, String uniqueFileName, String folderPath);

    // Read - All
    Page<Post> findAll(int page, int size);

    // Read - Detail
    Post getById(Long id);

    // Update
    @Transactional
    Post update(long id, PostDto request) throws IOException;

    // Delete
    @Transactional
    void delete(long id);

    // Categories
    List<Categories> findAllCategories();

    Page<Post> findByCategoryName(String name, Pageable pageable);

    // Search
    Page<Post> findByTitleContainingOrContextContaining(String query, Pageable pageable);
}
