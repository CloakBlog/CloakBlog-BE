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

    // Read - All

    // Read - Detail

    // Update

    // Delete

    // Categories

    Page<Post> findByCategoryName(String name, Pageable pageable);

    // Search
    Page<Post> findByTitleContainingOrContextContaining(String query, Pageable pageable);
}
