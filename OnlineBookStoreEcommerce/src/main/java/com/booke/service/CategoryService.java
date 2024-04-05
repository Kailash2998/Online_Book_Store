package com.booke.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.booke.model.Book;
import com.booke.model.Category;
import com.booke.repository.BookRepository;
import com.booke.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	BookRepository bookRepository;

	public List<Category> getAllCategory() {
		return categoryRepository.findAll();
	}
//	public Page<Book> getAllBookByCategory(PageRequest pageable) {
//		return bookRepository.findAll(pageable);
//	}
//
//	public Page<Book> getAllBookByCategory(Pageable pageable) {
//		return bookRepository.findAll(pageable);
//	}

	public void addCategory(Category category) {
		categoryRepository.save(category);

	}

	public void removeCategoryById(int id) {
		categoryRepository.deleteById(id);
	}

	public Optional<Category> getCategoryById(int id) {
		return categoryRepository.findById(id);
	}

	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	public Page<Category> findPaginated(int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		return categoryRepository.findAll(pageable);
	}

	public Page<Category> getAllCategories(PageRequest pageable) {
		return categoryRepository.findAll(pageable);
	}

	public Page<Category> findAll(Pageable pageable) {
		return categoryRepository.findAll(pageable);
	}

	public List<Category> searchCategories(String keyword) {
		return categoryRepository.findByNameContaining(keyword);
	}
}
