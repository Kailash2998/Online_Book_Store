package com.booke.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booke.model.OrderStatus;
import com.booke.model.PlaceOrder;
import com.booke.model.User;

public interface OrderRepository extends JpaRepository<PlaceOrder, Long> {

	List<PlaceOrder> findByUserAndOrderStatus(User user, OrderStatus status);

	List<PlaceOrder> findByOrderStatus(OrderStatus cancelled);

	List<PlaceOrder> findByUser(User user);

	List<PlaceOrder> findByUserOrderByIdDesc(User user);

}
