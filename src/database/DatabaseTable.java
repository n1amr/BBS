package database;

public enum DatabaseTable {
	BOOK("book"), BORROWER("borrower"), BORROWING("borrowing");

	private String name;

	private DatabaseTable(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
