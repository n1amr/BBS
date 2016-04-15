package database.models;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import database.Database;
import database.DatabaseTable;
import database.RawEntry;
import database.models.entities.Name;

public abstract class Borrower implements Model {
	protected int id;

	protected Name name;
	protected String ssn;

	public static final int MAX_BORROW = 0;

	public Borrower() {
		id = -1;
	}

	public Borrower(Name name, String ssn) {
		id = -1;
		this.name = name.getCopy();
		this.ssn = ssn;
	}

	public static Borrower load(int id) throws FileNotFoundException {
		RawEntry rawEntry = Database.getSingleton().load(DatabaseTable.BORROWER, id);

		return BorrowerFactory.load(rawEntry);
	}

	public Borrower(RawEntry rawEntry) {
		id = rawEntry.getId();
		String firstName = rawEntry.getData().get(0);
		String lastName = rawEntry.getData().get(1);
		this.name = new Name(firstName, lastName);
		this.ssn = rawEntry.getData().get(2);
	}
	public static ArrayList<Borrower> loadAll() throws FileNotFoundException {
		ArrayList<Borrower> borrowers = new ArrayList<Borrower>();

		ArrayList<RawEntry> rawEntries = Database.getSingleton().loadAll(DatabaseTable.BORROWER);
		for (RawEntry rawEntry : rawEntries)
			borrowers.add(BorrowerFactory.load(rawEntry));

		return borrowers;
	}

	public int getId() {
		return id;
	}

	public static final String TYPE_STUDENT = "<student>";
	public static final String TYPE_FACULTY = "<faculty>";

	@Override
	public RawEntry toRawEntry() {
		ArrayList<String> data = new ArrayList<String>();

		data.add(name.getFirstName());
		data.add(name.getLastName());
		data.add(ssn);

		return new RawEntry(id, data);
	}

	@Override
	public int commit() throws IOException {
		return Database.getSingleton().commit(DatabaseTable.BORROWER, toRawEntry());
	}

	@Override
	public void remove() {
		Database.getSingleton().removeEntry(DatabaseTable.BORROWER, id);
	}

	public int getMaxBorrow() {
		return MAX_BORROW;
	}
}
