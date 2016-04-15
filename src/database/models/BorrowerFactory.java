package database.models;

import database.RawEntry;

public class BorrowerFactory {

	public static Borrower load(RawEntry rawEntry) {
		Borrower borrower = null;
		String borrowerType = rawEntry.getData().get(3);
		if (borrowerType.equals(Borrower.TYPE_STUDENT))
			borrower = new Student(rawEntry);
		else if (borrowerType.equals(Borrower.TYPE_FACULTY))
			borrower = new Faculty(rawEntry);

		return borrower;
	}

}
