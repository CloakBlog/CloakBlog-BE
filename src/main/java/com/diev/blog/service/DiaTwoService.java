package com.diev.blog.service;

import com.diev.blog.domain.DiaTwoBlog;
import com.diev.blog.dto.BlogDto;
import jakarta.transaction.Transactional;

import java.util.List;

public interface DiaTwoService {

    // Create
    @Transactional
    DiaTwoBlog saveDiaTwoBlog(DiaTwoBlog request);

    // Read - All
    List<DiaTwoBlog> getAllDiaTwoBlog();

    // Read - Detail
    DiaTwoBlog getById(Long id);

    // Update
    @Transactional
    DiaTwoBlog updateDiaTwoBlog(long id, BlogDto request);

    // Delete
    @Transactional
    void delete(long id);

}
