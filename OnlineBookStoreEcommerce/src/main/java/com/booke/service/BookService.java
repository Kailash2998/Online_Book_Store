package com.booke.service;

import java.math.BigDecimal;
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
public class BookService {

	private CategoryRepository categoryRepository;

	@Autowired
	public void CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Autowired
	BookRepository bookRepository;

	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	public void removeBookById(long id) {
		bookRepository.deleteById(id);
	}

	public Optional<Book> getBookById(long id) {
		return bookRepository.findById(id);
	}

	public List<Book> getAllBookByCategoryId(int id) {
		return bookRepository.findAllByCategory_Id(id);
	}

	public void addBook(Book book) {
		bookRepository.save(book);

	}

	public List<Book> findAll() {
		// TODO Auto-generated method stub
		return bookRepository.findAll();
	}

	public Optional<Book> findById(Long bookId) {
		return bookRepository.findById(bookId);
	}

	public Book saveBook(Book book) {
		return bookRepository.save(book);
	}

	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	public List<Book> getBookPriceById(int bookId) {
		return bookRepository.findByBookId(bookId);
	}

	public List<Book> searchBooks(String keyword) {
		return bookRepository.findByNameContainingIgnoreCase(keyword);
	}

	public Page<Book> findPaginated(int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		return bookRepository.findAll(pageable);
	}

	public Page<Book> getAllBooks(PageRequest pageable) {
		return bookRepository.findAll(pageable);
	}

	public Page<Book> getAllBooks(Pageable pageable) {
		return bookRepository.findAll(pageable);
	}

	public Page<Category> getByCategory(PageRequest of) {
		// TODO Auto-generated method stub
		return categoryRepository.findAll(of);
	}
	
	public Page<Book> getAllBookByCategory(PageRequest pageable, int id) {
		return bookRepository.findAllByCategory_Id(id, pageable);
	}

	public Page<Book> getAllBookByCategory(Pageable pageable, int id) {
		return bookRepository.findAllByCategory_Id(id, pageable);
	}
	

}
