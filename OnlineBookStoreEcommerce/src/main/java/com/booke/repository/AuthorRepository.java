package com.booke.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.booke.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

	List<Author> findAll();

	Page<Author> findAll(Pageable pageable);
}
