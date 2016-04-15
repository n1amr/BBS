import java.io.IOException;

import database.models.Borrower;
import database.models.Faculty;
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
		System.out.println(borrower.getMaxBorrow());

		Borrower borrower2 = new Faculty(new Name("amr", "alaa"), "ssn", "engineering", "degree");
		int id2 = borrower2.commit();
		borrower2 = null;
		borrower2 = Borrower.load(id2);
		System.out.println(borrower2.getMaxBorrow());

		borrower.remove();
		borrower2.remove();
	}
}
