package database.models;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import database.Database;
import database.DatabaseTable;
import database.RawEntry;

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
		id = -1;
	}

	public Book(String isbn, String title, String author, int edition, int availability, String description, int rate) {
		id = -1;
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.edition = edition;
		this.availability = availability;
		this.description = description;
		this.rate = rate;
	}

	public static Book load(int id) throws FileNotFoundException {
		RawEntry rawEntry = Database.getSingleton().load(DatabaseTable.BOOK, id);

		Book book = new Book(rawEntry);
		return book;
	}

	public Book(RawEntry rawEntry) {
		id = rawEntry.getId();
		isbn = rawEntry.getData().get(0);
		title = rawEntry.getData().get(1);
		author = rawEntry.getData().get(2);
		edition = Integer.valueOf(rawEntry.getData().get(3));
		availability = Integer.valueOf(rawEntry.getData().get(4));
		description = rawEntry.getData().get(5);
		rate = Integer.valueOf(rawEntry.getData().get(6));
	}

	public RawEntry toRawEntry() {
		ArrayList<String> data = new ArrayList<String>();

		data.add(isbn);
		data.add(title);
		data.add(author);
		data.add(Integer.toString(edition));
		data.add(Integer.toString(availability));
		data.add(description);
		data.add(Integer.toString(rate));

		return new RawEntry(id, data);
	}

	public static ArrayList<Book> loadAll() throws FileNotFoundException {
		ArrayList<Book> books = new ArrayList<Book>();

		ArrayList<RawEntry> rawEntries = Database.getSingleton().loadAll(DatabaseTable.BOOK);
		for (RawEntry rawEntry : rawEntries)
			books.add(new Book(rawEntry));

		return books;
	}

	public void commit() throws FileNotFoundException {
		Database.getSingleton().commit(DatabaseTable.BOOK, toRawEntry());
	}

	public void remove() {
		Database.getSingleton().removeEntry(DatabaseTable.BOOK, id);
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "ISBN: " + isbn + ", " + "Title: " + title + ", " + "Author: " + author + ", " + "Edition: " + edition + ", " + "Availability: " + availability + ", " + "Description: " + description + ", " + "Rate: " + rate;
	}
}
