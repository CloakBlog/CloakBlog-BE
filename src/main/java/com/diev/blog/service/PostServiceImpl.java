package com.diev.blog.service;

import com.diev.blog.domain.*;

import com.diev.blog.dto.PostDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CategoriesRepository categoriesRepository;

    // Create

    // Read - All

    // Read - Detail

    // Update

    // Delete


    @Override
    public Page<Post> findByCategoryName(String categoryName, Pageable pageable) {
        Categories category = categoriesRepository.findByName(categoryName);
        return postRepository.findByCategoriesContains(category, pageable);
    }

    @Override
    public Page<Post> findByTitleContainingOrContextContaining(String query, Pageable pageable) {
        return postRepository.findByTitleContainingOrContextContaining(query, query, pageable);
    }

}
