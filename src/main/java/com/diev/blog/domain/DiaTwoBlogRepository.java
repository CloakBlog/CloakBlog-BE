package com.diev.blog.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaTwoBlogRepository extends JpaRepository<DiaTwoBlog, Long> {
    public DiaTwoBlog getById(long id);
}