package com.booke.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookId;

	@NotBlank(message = "Name is required")
	@Size(max = 255, message = "Name must be less than or equal to 255 characters")
	private String name;

	@NotBlank(message = "ISBN is required")
	private String isbn;

	@NotNull(message = "Price is required")
	@Positive(message = "Price must be positive")
	private BigDecimal price;

	@NotNull(message = "Quantity is required")
	@Min(value = 0, message = "Quantity must be greater than or equal to 0")
	private int quantity;

	@ManyToOne
	@JoinColumn(name = "author_id")
	@NotNull(message = "Author is required")
	private Author author;

	@ManyToOne
	@JoinColumn(name = "category_id", referencedColumnName = "category_id")
	@NotNull(message = "Category is required")
	private Category category;

	@Positive(message = "Weight must be positive")
	private double weight;

	@NotBlank(message = "Description is required")
	private String description;

	private String imageName;

	// (Getters and setters omitted for brevity)
	public Long getBookId() {
		return bookId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal d) {
		this.price = d;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", name=" + name + ", isbn=" + isbn + ", price=" + price + ", quantity="
				+ quantity + ", author=" + author + ", category=" + category + ", weight=" + weight + ", description="
				+ description + ", imageName=" + imageName + "]";
	}

	public void decreaseStock(int quanity) {
		// TODO Auto-generated method stub

	}

	@Transient // This annotation indicates that the field is not persisted
	private String authorName;

	public String getAuthorName() {
		return author != null ? author.getName() : null;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

}
