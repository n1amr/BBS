package database.models;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import database.Database;
import database.DatabaseTable;
import database.RawEntry;
import database.models.entities.Name;

public class Faculty extends Borrower implements Model {
	public static final int MAX_BORROW = 10;

	private String title;
	private String degree;

	public Faculty() {
		super();
	}

	public Faculty(Name name, String ssn, String title, String degree) {
		super(name, ssn);
		this.title = title;
		this.degree = degree;
	}

	public static Faculty load(int id) throws FileNotFoundException {
		RawEntry rawEntry = Database.getSingleton().load(DatabaseTable.BORROWER, id);

		Faculty Faculty = new Faculty(rawEntry);
		return Faculty;
	}

	public Faculty(RawEntry rawEntry) {
		super(rawEntry);

		this.title = rawEntry.getData().get(4);
		this.degree = rawEntry.getData().get(5);
	}

	@Override
	public RawEntry toRawEntry() {
		RawEntry rawEntry = super.toRawEntry();
		ArrayList<String> data = rawEntry.getData();

		data.add(Borrower.TYPE_FACULTY);
		data.add(title);
		data.add(degree);

		return rawEntry;
	}

	public int getMaxBorrow() {
		return MAX_BORROW;
	}
}
