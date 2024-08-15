package com.diev.blog.service;

import com.diev.blog.domain.DiaTwoBlog;
import com.diev.blog.domain.DiaTwoBlogRepository;

import com.diev.blog.dto.BlogDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class DiaTwoServiceImpl implements DiaTwoService {

    @Autowired
    DiaTwoBlogRepository diaTwoBlogRepository;

    // Create
    @Transactional
    @Override
    public DiaTwoBlog saveDiaTwoBlog(String title, String content, String img) {
        DiaTwoBlog blog = new DiaTwoBlog(title, content, img);

        return diaTwoBlogRepository.save(blog);
    }

    // Read - All
    @Override
    public List<DiaTwoBlog> getAllDiaTwoBlog() {
        return diaTwoBlogRepository.findAll().stream().toList();
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

        diaTwoBlog.update(blogDto.getTitle(), blogDto.getContent(), String.valueOf(blogDto.getImg()));
        return diaTwoBlog;
    }

    @Transactional
    @Override
    public void delete(long id) {
        diaTwoBlogRepository.deleteById(id);
    }

}
