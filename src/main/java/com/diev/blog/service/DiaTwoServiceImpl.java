package com.diev.blog.service;

import com.diev.blog.domain.DiaTwoBlog;
import com.diev.blog.domain.DiaTwoBlogRepository;
import com.diev.blog.dto.BlogDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DiaTwoServiceImpl implements DiaTwoService {

    @Autowired
    DiaTwoBlogRepository diaTwoBlogRepository;

    // Create
    @Transactional
    @Override
    public DiaTwoBlog saveDiaTwoBlog(DiaTwoBlog request) {
        return diaTwoBlogRepository.save(request);
    }

    // Read - All
    @Override
    public List<DiaTwoBlog> getAllDiaTwoBlog() {
        return diaTwoBlogRepository.findAll().stream().toList();
    }

    // Update
    @Transactional
    @Override
    public DiaTwoBlog updateDiaTwoBlog(Long id, BlogDto request) {
        DiaTwoBlog diaTwoBlog = diaTwoBlogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다!")
        );

        diaTwoBlog.update(request);
        return diaTwoBlog;
    }

    @Transactional
    @Override
    public void delete(long id) {
        diaTwoBlogRepository.deleteById(id);
    }
}
