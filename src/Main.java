import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import database.models.Book;
import database.models.Borrower;
import database.models.Borrowing;
import database.models.Faculty;
import database.models.Student;
import database.models.entities.Name;

public class Main {
	public static void main(String[] args) throws IOException {
		System.out.println("-------------");
		System.out.println("|  Welcome! |");
		System.out.println("-------------");

		Faculty faculty = new Faculty(new Name("Faculty", "Name"), "faculty_ssn", "facutly_title", "facutly_degree");
		faculty.commit();
		Borrower borrower = new Student(new Name("amr", "alaa"), "ssn", 10, faculty, 4);
		Book book = new Book("ibn", "title", "author", 1, 20, "description", 5);

		int borrower_id = borrower.commit();
		int book_id = book.commit();

		Borrowing borrowing = new Borrowing(book, borrower, new Date());
		int borrowing_id = borrowing.commit();

		ArrayList<Borrowing> borrowings = Borrowing.loadAll();
		System.out.print("[");
		for (Borrowing b : borrowings)
			System.out.print(b + ", ");
		System.out.println("{}]");
	}
}
