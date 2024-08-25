package com.diev.blog.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogMemberRepository extends JpaRepository<BlogMember, Integer> {
}
