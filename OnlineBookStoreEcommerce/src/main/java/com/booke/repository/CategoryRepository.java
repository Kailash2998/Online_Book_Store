package com.booke.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booke.model.Book;
import com.booke.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	List<Category> findAll();

	Page<Category> findAll(Pageable pageable);

	List<Category> findByNameContainingIgnoreCase(String keyword);

	List<Category> findByNameContaining(String keyword);


}
