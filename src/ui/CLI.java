package ui;

import java.io.IOException;
import java.util.Scanner;

import database.models.Book;

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
			System.out.print(">");
			response = Integer.valueOf(in.nextLine().trim());
			switch (response) {
			case 1:
				addNewBook();
				break;

			default:
				System.out.println("Invalid option");
				break;
			}

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
		System.out.print("Edition: ");
		int edition = Integer.valueOf(in.nextLine().trim());
		System.out.print("Available number of copies: ");
		int availability = Integer.valueOf(in.nextLine().trim());
		System.out.print("Description: ");
		String description = in.nextLine();
		System.out.print("Rate: ");
		int rate = Integer.valueOf(in.nextLine().trim());

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

	private void viewOptions() {
		System.out.println("1- Add a new book");
		System.out.println("2- Add a new borrower");

		System.out.println("0- Exit");

	}
}
