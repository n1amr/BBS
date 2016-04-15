package database.models;

import java.util.ArrayList;

public class Book {
	private int id;

	private String isbn;
	private String title;
	private String author;
	private int edition;
	private int availability;
	private String description;
	private int rate;

	public Book() {
		this.id = -1;
	}

	public Book(String isbn, String title, String author, int edition, int availability, String description, int rate) {
		this.id = -1;
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.edition = edition;
		this.availability = availability;
		this.description = description;
		this.rate = rate;
	}

	public static Book load(int id) {
		// TODO
		return null;
	}

	public static ArrayList<Book> loadAll() {
		// TODO
		return null;
	}

	public void commit() {
		// TODO
	}

	public void remove() {
		// TODO
	}

	public int getId() {
		return id;
	}

}
