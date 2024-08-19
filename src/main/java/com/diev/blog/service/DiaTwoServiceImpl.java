package com.diev.blog.service;

import com.diev.blog.domain.Categories;
import com.diev.blog.domain.CategoriesRepository;
import com.diev.blog.domain.DiaTwoBlog;
import com.diev.blog.domain.DiaTwoBlogRepository;

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
    DiaTwoBlogRepository diaTwoBlogRepository;

    @Autowired
    CategoriesRepository categoriesRepository;

    // Create
    @Transactional
    @Override
    public DiaTwoBlog saveDiaTwoBlog(String title, String content, String img, String folderPath, Set<Categories> categories) {
        DiaTwoBlog blog = new DiaTwoBlog(title, content, img, folderPath, categories);

        return diaTwoBlogRepository.save(blog);
    }

    // Read - All
    @Override
    public Page<DiaTwoBlog> getAllDiaTwoBlog(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return diaTwoBlogRepository.findAll(pageable);
    }

    // Read - Detail
    @Override
    public DiaTwoBlog getById(Long id) {
        return diaTwoBlogRepository.getById(id);
    }

    // Update
    @Transactional
    @Override
    public DiaTwoBlog updateDiaTwoBlog(long id, BlogDto blogDto) throws IOException {
        DiaTwoBlog diaTwoBlog = diaTwoBlogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다!")
        );

        diaTwoBlog.update(blogDto.getTitle(), blogDto.getContent(), String.valueOf(blogDto.getImg()), diaTwoBlog.getFolderPath(), diaTwoBlog.getCategories());
        return diaTwoBlog;
    }

    @Transactional
    @Override
    public void delete(long id) {
        diaTwoBlogRepository.deleteById(id);
    }

    @Override
    public List<Categories> getAllCategories() {
        return categoriesRepository.findAll();
    }

    @Override
    public Page<DiaTwoBlog> findByCategoryName(String categoryName, Pageable pageable) {
        Categories category = categoriesRepository.findByName(categoryName);
        return diaTwoBlogRepository.findByCategoriesContains(category, pageable);
    }

    @Override
    public Page<DiaTwoBlog> findByTitleContainingOrContextContaining(String query, Pageable pageable) {
        return diaTwoBlogRepository.findByTitleContainingOrContextContaining(query, query, pageable);
    }

}
