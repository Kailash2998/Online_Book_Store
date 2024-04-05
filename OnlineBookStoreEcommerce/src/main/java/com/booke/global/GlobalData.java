package com.booke.global;

import java.util.ArrayList;
import java.util.List;

import com.booke.model.Book;

public class GlobalData {

	public static List<Book> cart;
	static {
		cart = new ArrayList<Book>();
	}
}
