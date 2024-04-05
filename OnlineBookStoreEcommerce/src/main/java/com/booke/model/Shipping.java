//package com.booke.model;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.OneToOne;
//
//@Entity
//public class Shipping {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//
//	private String receiver;
//
//	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
//	@JoinColumn(name = "address_id")
//	private Address address;
//
//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "order_id", nullable = false)
//	private Order order;
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public Address getAddress() {
//		return address;
//	}
//
//	public void setAddress(Address address) {
//		this.address = address;
//	}
//
//	public String getReceiver() {
//		return receiver;
//	}
//
//	public void setReceiver(String receiver) {
//		this.receiver = receiver;
//	}
//
//	public Order getOrder() {
//		return order;
//	}
//
//	public void setOrder(Order order) {
//		this.order = order;
//	}
//
//	public void setAddress(org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address address2) {
//		// TODO Auto-generated method stub
//		
//	}
//
//}
