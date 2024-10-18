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
    @Transactional
    @Override
    public Post save(PostDto postDto, String uniqueFileName, String folderPath) {

        Post post = new Post(postDto, uniqueFileName, folderPath);

        return postRepository.save(post);
    }

    // Read - All
    @Override
    public Page<Post> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findAll(pageable);
    }

    // Read - Detail
    @Override
    public Post getById(Long id) {
        return postRepository.getReferenceById(id);
    }

    // Update
    @Transactional
    @Override
    public Post update(long id, PostDto postDto) throws IOException {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다!")
        );

        post.update(postDto.getTitle(), postDto.getContent(), String.valueOf(
                postDto.getImg()),
                post.getFolderPath(),
                post.getCategories()
        );
        return post;
    }

    @Transactional
    @Override
    public void delete(long id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<Categories> findAllCategories() {
        return categoriesRepository.findAll();
    }

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
