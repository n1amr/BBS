import java.io.FileNotFoundException;

import database.models.Book;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("-------------");
		System.out.println("|  Welcome! |");
		System.out.println("-------------");
		
		Book book = Book.load(1);
		System.out.println(book);
	}
}
