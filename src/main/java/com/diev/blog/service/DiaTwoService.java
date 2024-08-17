package com.diev.blog.service;

import com.diev.blog.domain.Categories;
import com.diev.blog.domain.DiaTwoBlog;

import com.diev.blog.dto.BlogDto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface DiaTwoService {

    // Create
    @Transactional
    DiaTwoBlog saveDiaTwoBlog(String title, String content, String img, String folderPath, Set<Categories> categories);

    // Read - All
    Page<DiaTwoBlog> getAllDiaTwoBlog(int page, int size);

    // Read - Detail
    DiaTwoBlog getById(Long id);

    // Update
    @Transactional
    DiaTwoBlog updateDiaTwoBlog(long id, BlogDto request) throws IOException;

    // Delete
    @Transactional
    void delete(long id);

    // Categories
    List<Categories> getAllCategories();

}
