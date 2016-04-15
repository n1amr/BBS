package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import database.models.Book;
import database.models.Borrower;
import database.models.Borrowing;
import database.models.Faculty;
import database.models.Student;
import database.models.entities.Name;

public class CLI {
	private Scanner in;

	public CLI() {
		in = new Scanner(System.in);
	}

	public void run() {
		System.out.println("Welcome to Book Borrowing System!");
		System.out.println("=================================");

		int response = -1;
		while (response != 0) {
			viewOptions();
			response = readInteger(">");
			switch (response) {
			case 1:
				addNewBook();
				break;
			case 2:
				addNewBorrower();
				break;
			case 3:
				borrowABook();
				break;
			case 4:
				returnABook();
				break;
			case 5:
				viewCatalogue();
				break;
			case 0:
				return;
			default:
				System.out.println("Invalid option");
				break;
			}
			System.out.println("Press ENTER to continue");
			in.nextLine();
		}
	}

	private void returnABook() {
		try {
			Borrower borrower = selectBorrower();

			ArrayList<Borrowing> borrowings = findBorrowerActiveBorrowings(borrower);
			if (borrowings.isEmpty()) {
				System.out.println("This borrower has no active borrowing");
				return;
			}

			ArrayList<Book> books = new ArrayList<Book>();
			for (Borrowing b : borrowings) {
				Book bk = b.getBook();
				books.add(bk);
				System.out.println("Title: " + bk.getTitle());
				System.out.println("ISBN: " + bk.getIsbn());
				System.out.println("----------");
			}

			Book book = selectBook(books);
			Borrowing borrowing = null;
			for (Borrowing b : borrowings)
				if (b.getBookId() == book.getId() && b.getBorrowerId() == borrower.getId()) {
					borrowing = b;
					break;
				}

			returnABook(borrowing, borrower, book);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void returnABook(Borrowing borrowing, Borrower borrower, Book book) throws IOException {
		int rate = readInteger("Rate this book (1-5): ");
		book.addRate(rate);

		borrowing.setReturned(true);
		borrower.setBorrowCount(borrower.getBorrowCount() - 1);
		book.setAvailability(book.getAvailability() + 1);

		book.commit();
		borrowing.commit();
		borrower.commit();
	}

	private void viewCatalogue() {
		try {
			ArrayList<Book> books = Book.loadAll();
			for (Book book : books) {
				System.out.println("Book #" + book.getId());
				System.out.println("Title: " + book.getTitle());
				System.out.println("ISBN: " + book.getIsbn());
				System.out.println("Description: " + book.getDescription());
				System.out.println("----------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void borrowABook() {
		try {
			Book book = selectBook();
			Borrower borrower = selectBorrower();

			boolean borrowedBefore = false;
			ArrayList<Borrowing> borrowings = findBookActiveBorrowings(book);
			for (Borrowing b : borrowings)
				if (b.getBorrowerId() == borrower.getId()) {
					borrowedBefore = true;
					break;
				}

			if (borrowedBefore)
				System.out.println("This borrower has already borrowed a copy of this book");
			else if (!borrower.canBorrow())
				System.out.println("Borrower has reached their borrow limit.");
			else if (book.getAvailability() == 0)
				System.out.println("Book has not available copy for borrowing.");
			else {
				createBorrowing(borrower, book);
				System.out.println("Book was successfully borrowed");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ArrayList<Borrowing> findBookActiveBorrowings(Book book) throws FileNotFoundException {
		ArrayList<Borrowing> borrowings = Borrowing.loadAll();
		ArrayList<Borrowing> activeBorrowings = new ArrayList<Borrowing>();
		for (Borrowing b : borrowings)
			if (b.getBookId() == book.getId() && !b.isReturned())
				activeBorrowings.add(b);

		return activeBorrowings;
	}

	private ArrayList<Borrowing> findBorrowerActiveBorrowings(Borrower borrower) throws FileNotFoundException {
		ArrayList<Borrowing> borrowings = Borrowing.loadAll();
		ArrayList<Borrowing> activeBorrowings = new ArrayList<Borrowing>();
		for (Borrowing b : borrowings)
			if (b.getBorrowerId() == borrower.getId() && !b.isReturned())
				activeBorrowings.add(b);

		return activeBorrowings;
	}

	private Borrower selectBorrower() throws FileNotFoundException {
		Borrower borrower = null;
		ArrayList<Borrower> borrowers = Borrower.loadAll();
		while (borrower == null) {
			System.out.print("Enter borrower's SSN: ");
			String ssn = in.nextLine();

			for (Borrower b : borrowers)
				if (b.getSsn().equals(ssn)) {
					borrower = b;
					break;
				}
			if (borrower == null)
				System.out.println("Borrower was not found");
		}

		return borrower;
	}

	private Book selectBook() throws FileNotFoundException {
		return selectBook(Book.loadAll());
	}

	private Book selectBook(ArrayList<Book> books) throws FileNotFoundException {
		Book book = null;
		while (book == null) {
			System.out.print("Enter book's ISBN: ");
			String isbn = in.nextLine();

			for (Book b : books)
				if (b.getIsbn().equals(isbn)) {
					book = b;
					break;
				}
			if (book == null)
				System.out.println("Book was not found");
		}
		return book;
	}

	private void createBorrowing(Borrower borrower, Book book) throws IOException {
		Borrowing borrowing = new Borrowing(book, borrower, new Date());
		borrower.setBorrowCount(borrower.getBorrowCount() + 1);
		book.setAvailability(book.getAvailability() - 1);

		book.commit();
		borrowing.commit();
		borrower.commit();
	}

	private void addNewBook() {
		System.out.println("Enter new book information!");
		System.out.print("ISBN: ");
		String isbn = in.nextLine();
		System.out.print("Title: ");
		String title = in.nextLine();
		System.out.print("Author: ");
		String author = in.nextLine();

		int edition = readInteger("Edition: ");
		int availability = readInteger("Available number of copies: ");

		System.out.print("Description: ");
		String description = in.nextLine();

		// int rate = readInteger("Rate (0-5): ", 0, 5);

		Book book = new Book(isbn, title, author, edition, availability, description, 0, 0);
		int id = -1;
		try {
			id = book.commit();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (id > 0)
			System.out.println("Book was saved successfully");
		else
			System.out.println("Error saving book");
	}

	private int readInteger(String prompt) {
		return readInteger(prompt, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	private int readInteger(String prompt, int min, int max) {
		int n = Integer.MAX_VALUE;
		boolean successful = false;
		while (!successful)
			try {
				System.out.print(prompt);
				n = Integer.valueOf(in.nextLine().trim());
				successful = n >= min && n <= max;
				if (!successful) {
					System.out.println("Input is out of range");
					continue;
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid integer number");
			}
		return n;
	}

	private void addNewBorrower() {
		System.out.println("Enter new borrower type!");
		System.out.println("1- Student");
		System.out.println("2- Faculty");
		int type = readInteger(">");
		switch (type) {
		case 1:
			addNewStudent();
			break;
		case 2:
			addNewFaculty();
			break;
		default:
			System.out.println("Invalid input");
			break;
		}
	}

	private void addNewFaculty() {
		System.out.println("Enter new faculty information!");
		System.out.print("First name: ");
		String firstName = in.nextLine();
		System.out.print("Last name: ");
		String lastName = in.nextLine();
		Name name = new Name(firstName, lastName);

		System.out.print("SSN: ");
		String ssn = in.nextLine();
		int borrowCount = 0;

		System.out.print("Title: ");
		String title = in.nextLine();
		System.out.print("Degree: ");
		String degree = in.nextLine();

		Faculty faculty = new Faculty(name, ssn, borrowCount, title, degree);

		int id = -1;
		try {
			id = faculty.commit();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (id > 0)
			System.out.println("Faculty was saved successfully");
		else
			System.out.println("Error saving faculty");
	}

	private void addNewStudent() {
		System.out.println("Enter new student information!");
		System.out.print("First name: ");
		String firstName = in.nextLine();
		System.out.print("Last name: ");
		String lastName = in.nextLine();
		Name name = new Name(firstName, lastName);

		System.out.print("SSN: ");
		String ssn = in.nextLine();

		int borrowCount = 0;

		int student_id_number = readInteger("ID: ");
		int faculty_id = readInteger("Faculty ID: ");
		int level = readInteger("Level: ");

		Student student = new Student(name, ssn, borrowCount, student_id_number, faculty_id, level);

		int id = -1;
		try {
			id = student.commit();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (id > 0)
			System.out.println("Student was saved successfully");
		else
			System.out.println("Error saving student");
	}

	private void viewOptions() {
		System.out.println("1- Add a new book");
		System.out.println("2- Add a new borrower");
		System.out.println("3- Borrow a book");
		System.out.println("4- Return a book");
		System.out.println("5- View books catalogue");

		System.out.println("0- Exit");
	}
}
