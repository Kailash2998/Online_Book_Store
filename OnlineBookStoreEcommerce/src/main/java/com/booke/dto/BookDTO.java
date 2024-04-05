package com.booke.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class BookDTO {

    private Long bookId;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "ISBN is required")
    private String isbn;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    @Positive(message = "Quantity must be positive")
    private int quantity;

    @NotNull(message = "Author ID is required")
    private int authorId;

    @NotNull(message = "Category ID is required")
    private int categoryId;

    @Positive(message = "Weight must be positive")
    private double weight;

    @NotBlank(message = "Description is required")
    @Size(min = 10, message = "Description must be at least 10 characters long")
    private String description;

//    @NotBlank(message = "Image name is required")
    private String imageName;

    
	public Long getBookId() {
		return bookId;
	}
	public void setBookId(Long bookId) {
		this.bookId = bookId;
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
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
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

	public BookDTO(Long bookId, String name, String isbn,
			BigDecimal price, int quantity, int authorId, int categoryId,
			double weight, String description, String imageName) {
		super();
		this.bookId = bookId;
		this.name = name;
		this.isbn = isbn;
		this.price = price;
		this.quantity = quantity;
		this.authorId = authorId;
		this.categoryId = categoryId;
		this.weight = weight;
		this.description = description;
		this.imageName = imageName;
	}
	public BookDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "BookDTO [bookId=" + bookId + ", name=" + name + ", isbn=" + isbn + ", price=" + price + ", quantity="
				+ quantity + ", authorId=" + authorId + ", categoryId=" + categoryId + ", weight=" + weight
				+ ", description=" + description + ", imageName=" + imageName + "]";
	}

	
}
