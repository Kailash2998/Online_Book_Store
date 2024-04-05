package com.booke.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.booke.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	List<Book> findAllByCategory_Id(int id);

	List<Book> findByBookId(int bookId);

	List<Book> findByNameContainingIgnoreCase(String keyword);

	Page<Book> findAll(Pageable pageable);
	
	Page<Book> findAllByCategory_Id(int id, Pageable pageable);
}
