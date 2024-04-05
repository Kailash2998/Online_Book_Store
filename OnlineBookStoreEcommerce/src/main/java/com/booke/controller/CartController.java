/*
 * package com.booke.controller;
 * 
 * import java.math.BigDecimal; import java.security.Principal; import
 * java.util.List; import java.util.Optional;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.security.authentication.
 * InsufficientAuthenticationException; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.PathVariable; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RequestParam; import
 * org.springframework.web.servlet.mvc.support.RedirectAttributes;
 * 
 * import com.booke.model.Book; import com.booke.model.Cart; import
 * com.booke.model.User; import com.booke.repository.CartRepository; import
 * com.booke.service.BookService; import com.booke.service.CartService; import
 * com.booke.service.UserService;
 * 
 * @Controller public class CartController {
 * 
 * @Autowired BookService bookService;
 * 
 * @Autowired UserService userService;
 * 
 * @Autowired CartService cartService;
 * 
 * @Autowired CartRepository cartRepository;
 * 
 * @RequestMapping("/addToCart/{id}") public String addToCart(@PathVariable Long
 * id, @RequestParam(defaultValue = "1") int quantity, Principal principal) { if
 * (principal != null) { Optional<Book> optionalBook = bookService.findById(id);
 * optionalBook.ifPresent(book -> { User user =
 * userService.findByEmail(principal.getName());
 * System.out.println("Book added to cart: " + book.getName());
 * cartService.addToCart(book.getBookId(), quantity, user); }); } else { return
 * "redirect:/login"; } return "redirect:/customer/profile"; }
 * 
 * @GetMapping("/cart") public String viewCart(Model model, Principal principal)
 * { if (principal != null) { List<Cart> cartItems =
 * cartService.getCartItems(principal.getName());
 * model.addAttribute("cartItems", cartItems);
 * 
 * // Calculate total amount BigDecimal totalAmount =
 * cartService.calculateTotalAmount(cartItems);
 * model.addAttribute("totalAmount", totalAmount);
 * System.out.println(totalAmount); return "cart"; } else { return
 * "redirect:/login"; } }
 * 
 * @GetMapping("/cart/removeItem/{itemId}") public String
 * removeCartItem(@PathVariable("itemId") Long itemId) {
 * cartService.removeCartItem(itemId); return "redirect:/cart"; }
 * 
 * @GetMapping("/cart/editItem/{itemId}") public String
 * editCartItem(@PathVariable("itemId") Long itemId, Model model) { Cart
 * cartItem = cartService.findCartItemById(itemId);
 * model.addAttribute("cartItem", cartItem);
 * 
 * return "cart/editCartItem"; }
 * 
 * @PostMapping("/cart/editItem/{itemId}") public String
 * updateCartItem(@PathVariable("itemId") Long itemId, @RequestParam("quanity")
 * int quanity, RedirectAttributes redirectAttributes) {
 * cartService.updateCartItemQuantity(itemId, quanity);
 * redirectAttributes.addFlashAttribute("successMessage",
 * "Cart item updated successfully"); return "redirect:/cart"; } }
 */

package com.booke.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.booke.model.Book;
import com.booke.model.Cart;
import com.booke.model.User;
import com.booke.repository.CartRepository;
import com.booke.service.BookService;
import com.booke.service.CartService;
import com.booke.service.UserService;

@Controller
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    BookService bookService;

    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;

    @Autowired
    CartRepository cartRepository;

    @RequestMapping("/addToCart/{id}")
    public String addToCart(@PathVariable Long id, @RequestParam(defaultValue = "1") int quantity,
                            Principal principal) {
        if (principal != null) {
            Optional<Book> optionalBook = bookService.findById(id);
            optionalBook.ifPresent(book -> {
                User user = userService.findByEmail(principal.getName());
                logger.info("Adding book {} to cart for user {}", book.getName(), user.getUsername());
                cartService.addToCart(book.getBookId(), quantity, user);
            });
        } else {
            logger.warn("User is not authenticated. Redirecting to login page.");
            return "redirect:/login";
        }
        return "redirect:/customer/profile";
    }

    @GetMapping("/cart")
    public String viewCart(Model model, Principal principal) {
        if (principal != null) {
            logger.info("Displaying cart for user: {}", principal.getName());
            List<Cart> cartItems = cartService.getCartItems(principal.getName());
            model.addAttribute("cartItems", cartItems);

            // Calculate total amount
            BigDecimal totalAmount = cartService.calculateTotalAmount(cartItems);
            model.addAttribute("totalAmount", totalAmount);
            logger.info("Total amount in cart: {}", totalAmount);
            return "cart";
        } else {
            logger.warn("User is not authenticated. Redirecting to login page.");
            return "redirect:/login";
        }
    }

    @GetMapping("/cart/removeItem/{itemId}")
    public String removeCartItem(@PathVariable("itemId") Long itemId) {
        cartService.removeCartItem(itemId);
        logger.info("Removed item with ID {} from cart", itemId);
        return "redirect:/cart";
    }

    @GetMapping("/cart/editItem/{itemId}")
    public String editCartItem(@PathVariable("itemId") Long itemId, Model model) {
        Cart cartItem = cartService.findCartItemById(itemId);
        model.addAttribute("cartItem", cartItem);
        return "cart/editCartItem";
    }

    @PostMapping("/cart/editItem/{itemId}")
    public String updateCartItem(@PathVariable("itemId") Long itemId, @RequestParam("quanity") int quantity,
                                 RedirectAttributes redirectAttributes) {
        cartService.updateCartItemQuantity(itemId, quantity);
        redirectAttributes.addFlashAttribute("successMessage", "Cart item updated successfully");
        logger.info("Updated quantity for item with ID {} to {}", itemId, quantity);
        return "redirect:/cart";
    }
}
