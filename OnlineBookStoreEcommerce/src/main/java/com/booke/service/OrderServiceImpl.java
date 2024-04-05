package com.booke.service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.booke.model.Book;
import com.booke.model.PlaceOrder;
import com.booke.model.User;
import com.booke.repository.BookRepository;
import com.booke.repository.OrderRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	BookRepository bookRepository;

	@Override
	public PlaceOrder saveOrder(PlaceOrder order) {
		return orderRepository.save(order);
	}

	@Override
	public List<PlaceOrder> getAllOrders() {
		return orderRepository.findAll();
	}

	@Override
	public PlaceOrder getOrderById(Long orderId) {
		return orderRepository.findById(orderId).orElse(null);
	}

	@Override
	public byte[] generateInvoiceByOrder(Long orderId) throws DocumentException {
		// Fetch order details from the database
		PlaceOrder order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

		Document document = new Document();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, byteArrayOutputStream);

		document.open();
		addInvoiceHeader(document);
		addOrderDetails(document, order);

		document.close();
		return byteArrayOutputStream.toByteArray();
	}

	private void addOrderDetails(Document document, PlaceOrder order) throws DocumentException {
		Font fontOrderDetails = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
		Paragraph orderDetails = new Paragraph();
		orderDetails.setAlignment(Element.ALIGN_LEFT);
		orderDetails.setFont(fontOrderDetails);

		orderDetails.add("Order ID: " + order.getId());
		orderDetails.add(Chunk.NEWLINE);
		orderDetails.add("Order Status: " + order.getOrderStatus());
		orderDetails.add(Chunk.NEWLINE);
		orderDetails.add("Total Amount: $" + order.getTotalAmount());

		document.add(orderDetails);
	}

	private static void addInvoiceHeader(Document document) {
		try {
			Paragraph header = new Paragraph("Invoice Header");
			header.setAlignment(Element.ALIGN_CENTER);
			document.add(header);
			document.add(new Paragraph("\n")); // Add some space after the header
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<PlaceOrder> findByUser(User user) {
		return orderRepository.findByUser(user);
	}

	@Override
	public List<PlaceOrder> getOrderHistoryForUser(User user) {
		return orderRepository.findByUser(user);
	}

	@Override
	public List<PlaceOrder> trackOrdersForUser(User user) {

		return orderRepository.findByUser(user);
	}

	@Override
	public Page<PlaceOrder> getOrders(PageRequest pageRequest) {
		return orderRepository.findAll(pageRequest);
	}

	@Override
	public Page<PlaceOrder> getAllOrders(PageRequest of) {
		Sort pageRequest = null;
		
		return (Page<PlaceOrder>) orderRepository.findAll(pageRequest);
	}

}
