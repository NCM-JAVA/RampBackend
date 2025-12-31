package com.ramp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ramp.entity.Content;
import com.ramp.entity.Users;
import com.ramp.enums.ContentStatus;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

    List<Content> findByStatus(ContentStatus status);

    List<Content> findByCreatedBy(Users user);

    List<Content> findByCreatedByOrderByCreatedDateDesc(Users user);

    List<Content> findByStatusOrderByCreatedDateDesc(ContentStatus status);

    List<Content> findAllByOrderByCreatedDateDesc();

    long countByStatus(ContentStatus status);
}
