/*
 * package com.booke.model;
 * 
 * import java.math.BigDecimal;
 * 
 * import jakarta.persistence.Column; import jakarta.persistence.Entity; import
 * jakarta.persistence.EnumType; import jakarta.persistence.Enumerated; import
 * jakarta.persistence.GeneratedValue; import
 * jakarta.persistence.GenerationType; import jakarta.persistence.Id; import
 * jakarta.persistence.JoinColumn; import jakarta.persistence.ManyToOne; import
 * jakarta.persistence.OneToOne;
 * 
 * @Entity public class PlaceOrder {
 * 
 * @Id
 * 
 * @GeneratedValue(strategy = GenerationType.AUTO) private Long id;
 * 
 * private String shippingAddress; private String phoneNumber; private String
 * receiverName; private String pinCode;
 * 
 * @OneToOne
 * 
 * @JoinColumn(name = "cart_id", nullable = false) private Cart cart;
 * 
 * 
 * 
 * @Enumerated(EnumType.STRING)
 * 
 * @Column(nullable = false) private OrderStatus orderStatus;
 * 
 * @ManyToOne
 * 
 * @JoinColumn(name = "user_id", nullable = false) private User user;
 * 
 * 
 * // New fields for payment
 * 
 * @Enumerated(EnumType.STRING) private PaymentMethod paymentMethod;
 * 
 * @Enumerated(EnumType.STRING) private PaymentOption paymentOption;
 * 
 * @Column(name = "total_amount", precision = 38, scale = 2) private BigDecimal
 * totalAmount;
 * 
 * public OrderStatus getOrderStatus() { return orderStatus; }
 * 
 * public void setOrderStatus(OrderStatus orderStatus) { this.orderStatus =
 * orderStatus; }
 * 
 * 
 * 
 * public User getUser() { return user; }
 * 
 * public void setUser(User user) { this.user = user; }
 * 
 * 
 * 
 * public BigDecimal getTotalAmount() { return totalAmount; }
 * 
 * public PlaceOrder(Long id, String shippingAddress, String phoneNumber, String
 * receiverName, String pinCode, Cart cart, boolean cancelled, OrderStatus
 * orderStatus, User user, PaymentMethod paymentMethod, PaymentOption
 * paymentOption, BigDecimal totalAmount) { super(); this.id = id;
 * this.shippingAddress = shippingAddress; this.phoneNumber = phoneNumber;
 * this.receiverName = receiverName; this.pinCode = pinCode; this.cart = cart;
 * this.orderStatus = orderStatus; this.user = user; this.paymentMethod =
 * paymentMethod; this.paymentOption = paymentOption; this.totalAmount =
 * totalAmount; }
 * 
 * public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount =
 * totalAmount; }
 * 
 * public Long getId() { return id; }
 * 
 * public void setId(Long id) { this.id = id; }
 * 
 * public String getShippingAddress() { return shippingAddress; }
 * 
 * public void setShippingAddress(String shippingAddress) { this.shippingAddress
 * = shippingAddress; }
 * 
 * public String getPhoneNumber() { return phoneNumber; }
 * 
 * public void setPhoneNumber(String phoneNumber) { this.phoneNumber =
 * phoneNumber; }
 * 
 * public String getReceiverName() { return receiverName; }
 * 
 * public void setReceiverName(String receiverName) { this.receiverName =
 * receiverName; }
 * 
 * public String getPinCode() { return pinCode; }
 * 
 * public void setPinCode(String pinCode) { this.pinCode = pinCode; }
 * 
 * public Cart getCart() { return cart; }
 * 
 * public void setCart(Cart cart) { this.cart = cart; }
 * 
 * public PaymentMethod getPaymentMethod() { return paymentMethod; }
 * 
 * public void setPaymentMethod(PaymentMethod paymentMethod) {
 * this.paymentMethod = paymentMethod; }
 * 
 * public PaymentOption getPaymentOption() { return paymentOption; }
 * 
 * public void setPaymentOption(PaymentOption paymentOption) {
 * this.paymentOption = paymentOption; }
 * 
 * public PlaceOrder() { super(); // TODO Auto-generated constructor stub }
 * 
 * @Override public String toString() { return "PlaceOrder [id=" + id +
 * ", shippingAddress=" + shippingAddress + ", phoneNumber=" + phoneNumber +
 * ", receiverName=" + receiverName + ", pinCode=" + pinCode + ", cart=" + cart
 * + ", orderStatus=" + orderStatus + ", user=" + user + ", paymentMethod=" +
 * paymentMethod + ", paymentOption=" + paymentOption + ", totalAmount=" +
 * totalAmount + "]"; }
 * 
 * 
 * 
 * 
 * 
 * }
 */

package com.booke.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class PlaceOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "Shipping address is required")
	private String shippingAddress;

	@NotBlank(message = "Phone number is required")
	@Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
	private String phoneNumber;

	@NotBlank(message = "Receiver name is required")
	private String receiverName;

	@NotBlank(message = "Pin code is required")
	@Pattern(regexp = "\\d{6}", message = "Pin code must be 6 digits")
	private String pinCode;

	@OneToOne
	@JoinColumn(name = "cart_id", nullable = false)
	@NotNull(message = "Cart is required")
	private Cart cart;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	@NotNull(message = "Order status is required")
	private OrderStatus orderStatus;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	// New fields for payment
	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;

	@Enumerated(EnumType.STRING)
	private PaymentOption paymentOption;

	@Column(name = "total_amount", precision = 38, scale = 2)
	private BigDecimal totalAmount;

	// Getters and setters
	// (Getters and setters omitted for brevity)

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public PaymentOption getPaymentOption() {
		return paymentOption;
	}

	public void setPaymentOption(PaymentOption paymentOption) {
		this.paymentOption = paymentOption;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public PlaceOrder(Long id, @NotBlank(message = "Shipping address is required") String shippingAddress,
			@NotBlank(message = "Phone number is required") @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits") String phoneNumber,
			@NotBlank(message = "Receiver name is required") String receiverName,
			@NotBlank(message = "Pin code is required") @Pattern(regexp = "\\d{6}", message = "Pin code must be 6 digits") String pinCode,
			@NotNull(message = "Cart is required") Cart cart,
			@NotNull(message = "Order status is required") OrderStatus orderStatus, User user,
			PaymentMethod paymentMethod, PaymentOption paymentOption, BigDecimal totalAmount) {
		super();
		this.id = id;
		this.shippingAddress = shippingAddress;
		this.phoneNumber = phoneNumber;
		this.receiverName = receiverName;
		this.pinCode = pinCode;
		this.cart = cart;
		this.orderStatus = orderStatus;
		this.user = user;
		this.paymentMethod = paymentMethod;
		this.paymentOption = paymentOption;
		this.totalAmount = totalAmount;
	}

	public PlaceOrder() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "PlaceOrder [id=" + id + ", shippingAddress=" + shippingAddress + ", phoneNumber=" + phoneNumber
				+ ", receiverName=" + receiverName + ", pinCode=" + pinCode + ", cart=" + cart + ", orderStatus="
				+ orderStatus + ", user=" + user + ", paymentMethod=" + paymentMethod + ", paymentOption="
				+ paymentOption + ", totalAmount=" + totalAmount + "]";
	}

}
