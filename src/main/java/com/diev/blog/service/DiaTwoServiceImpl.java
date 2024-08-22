package com.diev.blog.service;

import com.diev.blog.domain.Categories;
import com.diev.blog.domain.CategoriesRepository;
import com.diev.blog.domain.Blog;
import com.diev.blog.domain.BlogRepository;

import com.diev.blog.dto.BlogDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
public class DiaTwoServiceImpl implements DiaTwoService {

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    CategoriesRepository categoriesRepository;

    // Create
    @Transactional
    @Override
    public Blog saveDiaTwoBlog(String title, String content, String img, String folderPath, Set<Categories> categories) {
        Blog blog = new Blog(title, content, img, folderPath, categories);

        return blogRepository.save(blog);
    }

    // Read - All
    @Override
    public Page<Blog> getAllDiaTwoBlog(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return blogRepository.findAll(pageable);
    }

    // Read - Detail
    @Override
    public Blog getById(Long id) {
        return blogRepository.getById(id);
    }

    // Update
    @Transactional
    @Override
    public Blog updateDiaTwoBlog(long id, BlogDto blogDto) throws IOException {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다!")
        );

        blog.update(blogDto.getTitle(), blogDto.getContent(), String.valueOf(blogDto.getImg()), blog.getFolderPath(), blog.getCategories());
        return blog;
    }

    @Transactional
    @Override
    public void delete(long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public List<Categories> getAllCategories() {
        return categoriesRepository.findAll();
    }

    @Override
    public Page<Blog> findByCategoryName(String categoryName, Pageable pageable) {
        Categories category = categoriesRepository.findByName(categoryName);
        return blogRepository.findByCategoriesContains(category, pageable);
    }

    @Override
    public Page<Blog> findByTitleContainingOrContextContaining(String query, Pageable pageable) {
        return blogRepository.findByTitleContainingOrContextContaining(query, query, pageable);
    }

}
