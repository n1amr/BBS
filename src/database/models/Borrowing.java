
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

	private boolean returned = false;

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
		this(book.getId(), borrower.getId(), date);
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
		returned = rawEntry.getData().get(3).equals("true");
	}

	@Override
	public RawEntry toRawEntry() {
		ArrayList<String> data = new ArrayList<String>();

		data.add(Integer.toString(book_id));
		data.add(Integer.toString(borrower_id));
		data.add(Long.toString(date.getTime()));
		data.add(Boolean.toString(returned));

		return new RawEntry(id, data);
	}

	public static ArrayList<Borrowing> loadAll() throws FileNotFoundException {
		ArrayList<Borrowing> borrowings = new ArrayList<Borrowing>();

		ArrayList<RawEntry> rawEntries = Database.getSingleton().loadAll(DatabaseTable.BORROWING);
		for (RawEntry rawEntry : rawEntries)
			borrowings.add(new Borrowing(rawEntry));

		return borrowings;
	}

	@Override
	public int commit() throws IOException {
		return id = Database.getSingleton().commit(DatabaseTable.BORROWING, toRawEntry());
	}

	@Override
	public void remove() {
		Database.getSingleton().removeEntry(DatabaseTable.BORROWING, id);
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		try {
			return "{\"id\": " + id + ", \"Book\": " + getBook().toString() + ", \"Borrower\": " + getBorrower().toString() + ", \"Date\": \"" + date + "\"}";
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Borrowing) {
			Borrowing b = (Borrowing) obj;
			return b.getBookId() == getBookId() && b.getBorrowerId() == getBorrowerId() && b.getDate().getTime() == getDate().getTime();
		}
		return false;
	}

	public int getBookId() {
		return book_id;
	}

	public Book getBook() throws FileNotFoundException {
		return Book.load(book_id);
	}

	public void setBookId(int book_id) {
		this.book_id = book_id;
	}

	public int getBorrowerId() {
		return borrower_id;
	}

	public Borrower getBorrower() throws FileNotFoundException {
		return Borrower.load(borrower_id);
	}

	public void setBorrowerId(int borrower_id) {
		this.borrower_id = borrower_id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isReturned() {
		return returned;
	}

	public void setReturned(boolean returned) {
		this.returned = returned;
	}

}
