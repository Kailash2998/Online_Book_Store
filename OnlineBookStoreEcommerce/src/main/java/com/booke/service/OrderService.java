package com.booke.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.booke.model.Cart;
import com.booke.model.PlaceOrder;
import com.booke.model.User;
import com.itextpdf.text.DocumentException;

public interface OrderService {

	PlaceOrder saveOrder(PlaceOrder order);

	List<PlaceOrder> getAllOrders();

	PlaceOrder getOrderById(Long orderId);

	byte[] generateInvoiceByOrder(Long orderId) throws DocumentException;

	List<PlaceOrder> findByUser(User user);

	List<PlaceOrder> getOrderHistoryForUser(User user);

	List<PlaceOrder> trackOrdersForUser(User user);

	Page<PlaceOrder> getOrders(PageRequest of);

	Page<PlaceOrder> getAllOrders(PageRequest of);


}
