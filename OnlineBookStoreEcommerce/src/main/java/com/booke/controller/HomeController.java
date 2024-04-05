/*
 * package com.booke.controller;
 * 
 * import java.security.Principal; import java.util.List; import
 * java.util.Optional;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.data.domain.Page; import
 * org.springframework.data.domain.PageRequest; import
 * org.springframework.data.domain.Sort; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.PathVariable; import
 * org.springframework.web.bind.annotation.RequestParam;
 * 
 * import com.booke.model.Book; import com.booke.model.User; import
 * com.booke.repository.BookRepository; import com.booke.service.AuthorService;
 * import com.booke.service.BookService; import
 * com.booke.service.CategoryService; import com.booke.service.UserService;
 * 
 * @Controller public class HomeController {
 * 
 * @Autowired CategoryService categoryService;
 * 
 * @Autowired BookService bookService;
 * 
 * @Autowired BookRepository bookRepository;
 * 
 * @Autowired UserService userService;
 * 
 * @Autowired AuthorService authorService;
 * 
 * @GetMapping("/index") public String index() { return "index"; }
 * 
 * @GetMapping("/shop") public String shop(Model
 * model, @RequestParam(defaultValue = "0") int page,
 * 
 * @RequestParam(defaultValue = "2") int size, @RequestParam(defaultValue =
 * "name") String sortBy) { Page<Book> books =
 * bookService.getAllBooks(PageRequest.of(page, size, Sort.by(sortBy)));
 * model.addAttribute("categories", categoryService.getAllCategory());
 * model.addAttribute("authors", authorService.getAllAuthor());
 * model.addAttribute("books", books); model.addAttribute("currentPage", page);
 * model.addAttribute("totalPages", books.getTotalPages());
 * model.addAttribute("sortBy", sortBy); return "shop"; }
 * 
 * @GetMapping("/shop/category/{id}") public String shopByCategory(Model
 * model, @PathVariable int id, @RequestParam(defaultValue = "0") int page,
 * 
 * @RequestParam(defaultValue = "20") int size) { Page<Book> books =
 * bookService.getAllBookByCategory(PageRequest.of(page, size), id);
 * model.addAttribute("categories", categoryService.getAllCategory());
 * model.addAttribute("books", books); return "shop"; }
 * 
 * @GetMapping("/shop/viewbook/{id}") public String viewBook(Model
 * model, @PathVariable("id") Long id) { Optional<Book> optionalBook =
 * bookService.getBookById(id);
 * 
 * if (optionalBook.isPresent()) { Book book = optionalBook.get();
 * model.addAttribute("book", book); } else { model.addAttribute("errorMessage",
 * "Book not found"); return "errorPage"; }
 * 
 * return "user/viewBook"; }
 * 
 * @GetMapping("/shop/search") public String
 * searchBooks(@RequestParam("keyword") String keyword, Model model) {
 * List<Book> books = bookService.searchBooks(keyword);
 * model.addAttribute("books", books); return "user/search-results"; }
 * 
 * 
 * 
 * }
 */

package com.booke.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.booke.model.Book;
import com.booke.model.User;
import com.booke.repository.BookRepository;
import com.booke.service.AuthorService;
import com.booke.service.BookService;
import com.booke.service.CategoryService;
import com.booke.service.UserService;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	CategoryService categoryService;

	@Autowired
	BookService bookService;

	@Autowired
	BookRepository bookRepository;

	@Autowired
	UserService userService;

	@Autowired
	AuthorService authorService;

	@GetMapping("/index")
	public String index() {
		logger.info("Displaying index page");
		return "index";
	}

	@GetMapping("/shop")
	public String shop(Model model, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "2") int size, @RequestParam(defaultValue = "name") String sortBy) {
		logger.info("Displaying shop page");
		Page<Book> books = bookService.getAllBooks(PageRequest.of(page, size, Sort.by(sortBy)));
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("authors", authorService.getAllAuthor());
		model.addAttribute("books", books);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", books.getTotalPages());
		model.addAttribute("sortBy", sortBy);
		return "shop";
	}

	@GetMapping("/shop/category/{id}")
	public String shopByCategory(Model model, @PathVariable int id, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {
		logger.info("Displaying books by category with ID: {}", id);
		Page<Book> books = bookService.getAllBookByCategory(PageRequest.of(page, size), id);
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("books", books);
		return "shop";
	}

	@GetMapping("/shop/viewbook/{id}")
	public String viewBook(Model model, @PathVariable("id") Long id) {
		logger.info("Viewing book with ID: {}", id);
		Optional<Book> optionalBook = bookService.getBookById(id);

		if (optionalBook.isPresent()) {
			Book book = optionalBook.get();
			model.addAttribute("book", book);
		} else {
			model.addAttribute("errorMessage", "Book not found");
			logger.error("Book not found with ID: {}", id);
			return "errorPage";
		}

		return "user/viewBook";
	}

	@GetMapping("/shop/search")
	public String searchBooks(@RequestParam("keyword") String keyword, Model model) {
		logger.info("Searching for books with keyword: {}", keyword);
		List<Book> books = bookService.searchBooks(keyword);
		model.addAttribute("books", books);
		return "user/search-results";
	}

}
