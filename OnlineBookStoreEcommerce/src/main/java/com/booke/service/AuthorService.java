package com.booke.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.booke.model.Author;
import com.booke.model.Category;
import com.booke.repository.AuthorRepository;

@Service
public class AuthorService {

	@Autowired
	AuthorRepository authorRepository;

	public List<Author> getAllAuthor() {
		return authorRepository.findAll();
	}

	public void addAuthor(Author author) {
		authorRepository.save(author);

	}

	public void removeAuthorById(int id) {
		authorRepository.deleteById(id);
	}

	public Optional<Author> getAuthorById(int id) {
		return authorRepository.findById(id);
	}

	public List<Author> getAllAuthors() {
		return authorRepository.findAll();

	}

	public Page<Author> findPaginated(int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		return authorRepository.findAll(pageable);
	}

	public Page<Author> getAllAuthors(PageRequest pageable) {
		return authorRepository.findAll(pageable);
	}

	public Page<Author> findAll(Pageable pageable) {
		return authorRepository.findAll(pageable);
	}
}
