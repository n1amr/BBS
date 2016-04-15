package database.models.entities;

public class Name {
	private String firstName;
	private String lastName;

	public Name(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Name getCopy() {
		return new Name(firstName, lastName);
	}

	@Override
	public String toString() {
		return firstName + " " + lastName;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Name && ((Name) obj).getFirstName().equals(firstName) && ((Name) obj).getLastName().equals(lastName);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
