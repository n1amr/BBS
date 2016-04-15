package database.models;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import database.Database;
import database.DatabaseTable;
import database.RawEntry;
import database.models.entities.Name;

public class Student extends Borrower implements Model {
	public static final int MAX_BORROW = 5;

	private int student_id;
	private Faculty faculty = null;
	private int faculty_id;
	private int level;

	public Student() {
		super();
	}

	public Student(Name name, String ssn, int student_id, int faculty_id, int level) {
		super(name, ssn);
		this.student_id = student_id;
		this.faculty_id = faculty_id;
		this.level = level;
	}

	public static Student load(int id) throws FileNotFoundException {
		RawEntry rawEntry = Database.getSingleton().load(DatabaseTable.BORROWER, id);

		Student student = new Student(rawEntry);
		return student;
	}

	public Student(RawEntry rawEntry) {
		super(rawEntry);

		this.student_id = Integer.valueOf(rawEntry.getData().get(4));
		this.faculty_id = Integer.valueOf(rawEntry.getData().get(5));
		this.level = Integer.valueOf(rawEntry.getData().get(6));
	}

	public Student(Name name, String ssn, int student_id, Faculty faculty, int level) {
		this(name, ssn, student_id, faculty.getId(), level);
	}

	@Override
	public RawEntry toRawEntry() {
		RawEntry rawEntry = super.toRawEntry();
		ArrayList<String> data = rawEntry.getData();

		data.add(Borrower.TYPE_STUDENT);
		data.add(Integer.toString(student_id));
		data.add(Integer.toString(faculty_id));
		data.add(Integer.toString(level));

		return rawEntry;
	}

	public int getMaxBorrow() {
		return MAX_BORROW;
	}
}
