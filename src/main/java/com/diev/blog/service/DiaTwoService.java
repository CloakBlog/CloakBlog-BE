package com.diev.blog.service;

import com.diev.blog.domain.DiaTwoBlog;

import com.diev.blog.dto.BlogDto;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.util.List;

public interface DiaTwoService {

    // Create
    @Transactional
    DiaTwoBlog saveDiaTwoBlog(String title, String content, byte[] img);

    // Read - All
    List<DiaTwoBlog> getAllDiaTwoBlog();

    // Read - Detail
    DiaTwoBlog getById(Long id);

    // Update
    @Transactional
    DiaTwoBlog updateDiaTwoBlog(long id, BlogDto request) throws IOException;

    // Delete
    @Transactional
    void delete(long id);

}
