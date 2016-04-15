
package database.models;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import database.Database;
import database.DatabaseTable;
import database.RawEntry;

public class Borrowing implements Model {
	private int id;

	private int book_id;
	private int borrower_id;
	private Date date;

	private Book book = null;
	private Borrower borrower = null;

	public Borrowing() {
		id = -1;
		date = new Date();
	}

	public Borrowing(int book_id, int borrower_id, Date date) {
		id = -1;
		this.book_id = book_id;
		this.borrower_id = borrower_id;
		this.date = date;
	}

	public Borrowing(Book book, Borrower borrower, Date date) {
		id = -1;
		this.book = book;
		this.book_id = book.getId();
		this.borrower = borrower;
		this.borrower_id = borrower.getId();
		this.date = date;
	}

	public static Borrowing load(int id) throws FileNotFoundException {
		RawEntry rawEntry = Database.getSingleton().load(DatabaseTable.BORROWING, id);

		Borrowing borrowing = new Borrowing(rawEntry);
		return borrowing;
	}

	public Borrowing(RawEntry rawEntry) {
		id = rawEntry.getId();
		book_id = Integer.valueOf(rawEntry.getData().get(0));
		borrower_id = Integer.valueOf(rawEntry.getData().get(1));
		date = new Date(Long.valueOf(rawEntry.getData().get(2)));
	}

	public RawEntry toRawEntry() {
		ArrayList<String> data = new ArrayList<String>();

		data.add(Integer.toString(book_id));
		data.add(Integer.toString(borrower_id));
		data.add(Long.toString(date.getTime()));

		return new RawEntry(id, data);
	}

	public static ArrayList<Borrowing> loadAll() throws FileNotFoundException {
		ArrayList<Borrowing> borrowings = new ArrayList<Borrowing>();

		ArrayList<RawEntry> rawEntries = Database.getSingleton().loadAll(DatabaseTable.BORROWING);
		for (RawEntry rawEntry : rawEntries)
			borrowings.add(new Borrowing(rawEntry));

		return borrowings;
	}

	public int commit() throws IOException {
		return Database.getSingleton().commit(DatabaseTable.BORROWING, toRawEntry());
	}

	public void remove() {
		Database.getSingleton().removeEntry(DatabaseTable.BORROWING, id);
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		// return "ISBN: " + isbn + ", " + "Title: " + title + ", " + "Author: " +
		// author + ", " + "Edition: " + edition + ", " + "Availability: " +
		// availability + ", " + "Description: " + description + ", " + "Rate: " +
		// rate;
		return "000;";
	}
}
