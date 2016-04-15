
package database.models;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import database.Database;
import database.DatabaseTable;
import database.RawEntry;

public class Book implements Model {
	private int id;

	private String isbn;
	private String title;
	private String author;
	private int edition;
	private int availability;
	private String description;
	private int rateSum;
	private int ratesCount;

	public Book() {
		id = -1;
	}

	public Book(String isbn, String title, String author, int edition, int availability, String description, int rateSum, int ratesCount) {
		id = -1;
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.edition = edition;
		this.availability = availability;
		this.description = description;
		this.rateSum = rateSum;
		this.ratesCount = ratesCount;
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
		rateSum = Integer.valueOf(rawEntry.getData().get(6));
		ratesCount = Integer.valueOf(rawEntry.getData().get(7));
	}

	@Override
	public RawEntry toRawEntry() {
		ArrayList<String> data = new ArrayList<String>();

		data.add(isbn);
		data.add(title);
		data.add(author);
		data.add(Integer.toString(edition));
		data.add(Integer.toString(availability));
		data.add(description);
		data.add(Integer.toString(rateSum));
		data.add(Integer.toString(ratesCount));

		return new RawEntry(id, data);
	}

	public static ArrayList<Book> loadAll() throws FileNotFoundException {
		ArrayList<Book> books = new ArrayList<Book>();

		ArrayList<RawEntry> rawEntries = Database.getSingleton().loadAll(DatabaseTable.BOOK);
		for (RawEntry rawEntry : rawEntries)
			books.add(new Book(rawEntry));

		return books;
	}

	@Override
	public int commit() throws IOException {
		return id = Database.getSingleton().commit(DatabaseTable.BOOK, toRawEntry());
	}

	@Override
	public void remove() {
		Database.getSingleton().removeEntry(DatabaseTable.BOOK, id);
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "{\"id\": " + id + ", \"ISBN\": \"" + isbn + "\", " + "\"Title\": \"" + title + "\", " + "\"Author\": \"" + author + "\", " + "\"Edition\": " + edition + ", " + "\"Availability\": " + availability + ", " + "\"Description\": \"" + description + "\", " + "\"Rate Sum\": " + rateSum + ", " + "\"Rates Count\": " + ratesCount + "\"Average Rate\": " + getAverageRate() + "}";
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Book && ((Book) obj).getIsbn().equals(isbn);
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getEdition() {
		return edition;
	}

	public void setEdition(int edition) {
		this.edition = edition;
	}

	public int getAvailability() {
		return availability;
	}

	public void setAvailability(int availability) {
		this.availability = availability;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getRateSum() {
		return rateSum;
	}

	public void setRateSum(int rateSum) {
		this.rateSum = rateSum;
	}

	public int getRatesCount() {
		return ratesCount;
	}

	public void setRatesCount(int ratesCount) {
		this.ratesCount = ratesCount;
	}

	public float getAverageRate() {
		return (ratesCount != 0) ? (((float) rateSum) / ratesCount) : 0;
	}

	public void addRate(int rate) {
		rateSum += rate;
		ratesCount++;
	}
}
