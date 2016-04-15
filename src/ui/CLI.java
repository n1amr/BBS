package ui;

import java.io.IOException;
import java.util.Scanner;

import database.models.Book;
import database.models.Faculty;
import database.models.Student;
import database.models.entities.Name;

public class CLI {
	private Scanner in;

	public CLI() {
		in = new Scanner(System.in);
	}

	public void run() {
		System.out.println("-------------");
		System.out.println("|  Welcome! |");
		System.out.println("-------------");

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

		int rate = readInteger("Rate (0-5): ", 0, 5);

		Book book = new Book(isbn, title, author, edition, availability, description, rate);
		int id = -1;
		try {
			id = book.commit();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (id > 0) {
			System.out.println("Book was saved successfully");
		} else {
			System.out.println("Error saving book");
		}

	}

	private int readInteger(String prompt) {
		return readInteger(prompt, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	private int readInteger(String prompt, int max) {
		return readInteger(prompt, Integer.MIN_VALUE, max);
	}

	private int readInteger(String prompt, int min, int max) {
		int n = Integer.MAX_VALUE;
		boolean successful = false;
		while (!successful) {
			try {
				System.out.print(prompt);
				n = Integer.valueOf(in.nextLine().trim());
				successful = (n >= min) && (n <= max);
				if (!successful) {
					System.out.println("Input is out of range");
					continue;
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid integer number");
			}
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

		System.out.print("Title: ");
		String title = in.nextLine();
		System.out.print("Degree: ");
		String degree = in.nextLine();

		Faculty faculty = new Faculty(name, ssn, title, degree);

		int id = -1;
		try {
			id = faculty.commit();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (id > 0) {
			System.out.println("Faculty was saved successfully");
		} else {
			System.out.println("Error saving faculty");
		}

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

		int student_id_number = readInteger("ID: ");
		int faculty_id = readInteger("Faculty ID: ");
		int level = readInteger("Level: ");

		Student student = new Student(name, ssn, student_id_number, faculty_id, level);

		int id = -1;
		try {
			id = student.commit();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (id > 0) {
			System.out.println("Student was saved successfully");
		} else {
			System.out.println("Error saving student");
		}

	}

	private void viewOptions() {
		System.out.println("1- Add a new book");
		System.out.println("2- Add a new borrower");

		System.out.println("0- Exit");

	}
}
