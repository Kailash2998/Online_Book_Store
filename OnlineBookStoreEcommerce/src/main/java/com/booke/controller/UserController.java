/*
 * package com.booke.controller;
 * 
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.dao.DataIntegrityViolationException; import
 * org.springframework.security.core.userdetails.UserDetailsService; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.validation.BindingResult; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.ModelAttribute; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.servlet.mvc.support.RedirectAttributes;
 * 
 * import com.booke.model.User; import com.booke.service.BookService; import
 * com.booke.service.UserService;
 * 
 * import jakarta.validation.Valid;
 * 
 * import org.springframework.security.core.Authentication; import
 * org.springframework.security.core.context.SecurityContextHolder;
 * 
 * @Controller public class UserController {
 * 
 * @Autowired UserDetailsService userDetailsService;
 * 
 * @Autowired UserService userService;
 * 
 * @Autowired BookService bookService;
 * 
 * @GetMapping("/") public String homePage() { return "user/home"; }
 * 
 * @GetMapping("/detail.html") public String detailPage() { return
 * "user/detail"; }
 * 
 * @GetMapping("/listing.html") public String listPage() { return
 * "user/listing"; }
 * 
 * @GetMapping("/registration") public String showRegistrationForm(Model model)
 * { model.addAttribute("user", new User()); return "user/register"; }
 * 
 * 
 * @PostMapping("/registration") public String
 * saveUser(@Valid @ModelAttribute("user") User user, BindingResult
 * bindingResult, Model model, RedirectAttributes redirectAttributes) { if
 * (bindingResult.hasErrors()) { return "user/register"; }
 * 
 * // Check if email already exists User existingUser =
 * userService.findByEmail(user.getEmail()); if (existingUser != null) {
 * model.addAttribute("errorMessage", "This email is already registered.");
 * return "user/register"; }
 * 
 * try { userService.save(user); redirectAttributes.addFlashAttribute("message",
 * "Registered Successfully"); return "redirect:/login"; } catch (Exception e) {
 * model.addAttribute("errorMessage", "An error occurred during registration.");
 * return "user/register"; } }
 * 
 * 
 * 
 * @GetMapping("/login") public String login(Model model) { Authentication
 * authentication = SecurityContextHolder.getContext().getAuthentication();
 * String username = authentication.getName(); model.addAttribute("username",
 * username); return "user/login"; }
 * 
 * @GetMapping("/admin-page") public String adminPage() { return "admin"; }
 * 
 * }
 */

package com.booke.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.booke.model.User;
import com.booke.service.BookService;
import com.booke.service.UserService;

import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	UserService userService;

	@Autowired
	BookService bookService;

	@GetMapping("/")
	public String homePage() {
		logger.info("Displaying home page");
		return "user/home";
	}

	@GetMapping("/detail.html")
	public String detailPage() {
		logger.info("Displaying detail page");
		return "user/detail";
	}

	@GetMapping("/listing.html")
	public String listPage() {
		logger.info("Displaying listing page");
		return "user/listing";
	}

	@GetMapping("/registration")
	public String showRegistrationForm(Model model) {
		logger.info("Displaying registration form");
		model.addAttribute("user", new User());
		return "user/register";
	}

//	@PostMapping("/registration")
//	public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model,
//			RedirectAttributes redirectAttributes) {
//		logger.info("Processing registration form submission");
//		if (bindingResult.hasErrors()) {
//			logger.error("Validation error in registration form submission");
//			return "user/register";
//		}
//
//		// Check if email already exists
//		User existingUser = userService.findByEmail(user.getEmail());
//		if (existingUser != null) {
//			logger.error("Registration failed: email already exists");
//			model.addAttribute("errorMessage", "This email is already registered.");
//			return "user/register";
//		}
//
//		try {
//			userService.save(user);
//			redirectAttributes.addFlashAttribute("message", "Registered Successfully");
//			logger.info("User registered successfully");
//			return "redirect:/login";
//		} catch (Exception e) {
//			logger.error("Error occurred during registration: {}", e.getMessage());
//			model.addAttribute("errorMessage", "An error occurred during registration.");
//			return "user/register";
//		}
//	}

	@PostMapping("/registration")
	public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model,
	        RedirectAttributes redirectAttributes) {
	    logger.info("Processing registration form submission");
	    if (bindingResult.hasErrors()) {
	        logger.error("Validation error in registration form submission");
	        return "user/register";
	    }

	    // Check if email already exists
	    User existingUser = userService.findByEmail(user.getEmail());
	    if (existingUser != null) {
	        logger.error("Registration failed: email already exists");
	        model.addAttribute("errorMessage", "This email is already registered.");
	        return "user/register";
	    }

	    try {
	        userService.save(user);
	        redirectAttributes.addFlashAttribute("message", "Registered Successfully");
	        logger.info("User registered successfully");
	        return "redirect:/login";
	    } catch (Exception e) {
	        logger.error("Error occurred during registration: {}", e.getMessage());
	        model.addAttribute("errorMessage", "An error occurred during registration.");
	        return "user/register";
	    }
	}

	
	@GetMapping("/login")
	public String login(Model model) {
		logger.info("Displaying login page");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		model.addAttribute("username", username);
		return "user/login";
	}

	@GetMapping("/admin-page")
	public String adminPage() {
		logger.info("Displaying admin page");
		return "admin";
	}

}
