import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import database.models.Book;
import database.models.Borrower;
import database.models.Borrowing;
import database.models.Faculty;
import database.models.Student;
import database.models.entities.Name;
import ui.CLI;

public class Main {
	public static void main(String[] args) throws IOException {
		CLI cli = new CLI();
		cli.run();
	}
}
