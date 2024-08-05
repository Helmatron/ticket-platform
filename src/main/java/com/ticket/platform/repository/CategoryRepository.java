package com.ticket.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ticket.platform.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}