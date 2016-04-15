import java.io.IOException;

import database.RawEntry;
import database.models.Book;
import database.models.Borrower;
import database.models.Student;
import database.models.entities.Name;

public class Main {
	public static void main(String[] args) throws IOException {
		System.out.println("-------------");
		System.out.println("|  Welcome! |");
		System.out.println("-------------");

		Borrower borrower = new Student(new Name("amr", "alaa"), "ssn", 10, 11, 4);
		int id = borrower.commit();
		borrower = null;
		borrower = Borrower.load(id);
		System.out.println(borrower.MAX_BORROW);
		borrower.remove();
	}
}
