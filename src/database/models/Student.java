package database.models;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import database.Database;
import database.DatabaseTable;
import database.RawEntry;
import database.models.entities.Name;

public class Student extends Borrower implements Model {
	public static int MAX_BORROW = 5;

	private int student_id_number;
	private String faculty;
	private int level;

	public Student() {
		super();
	}

	public Student(Name name, String ssn, int borrowCount, int student_id_number, String faculty, int level) {
		super(name, ssn, borrowCount);
		this.student_id_number = student_id_number;
		this.faculty = faculty;
		this.level = level;
	}

	public static Student load(int id) throws FileNotFoundException {
		RawEntry rawEntry = Database.getSingleton().load(DatabaseTable.BORROWER, id);

		Student student = new Student(rawEntry);
		return student;
	}

	public Student(RawEntry rawEntry) {
		super(rawEntry);

		student_id_number = Integer.valueOf(rawEntry.getData().get(5));
		faculty = rawEntry.getData().get(6);
		level = Integer.valueOf(rawEntry.getData().get(7));
	}

	@Override
	public RawEntry toRawEntry() {
		RawEntry rawEntry = super.toRawEntry();
		ArrayList<String> data = rawEntry.getData();

		data.add(Borrower.TYPE_STUDENT);
		data.add(Integer.toString(student_id_number));
		data.add(faculty);
		data.add(Integer.toString(level));

		return rawEntry;
	}

	@Override
	public String toString() {
		return "{ \"id\": " + id + ", \"Name\": \"" + name + "\", \"SSN\": \"" + ssn + "\", \"IDNumber\": \"" + student_id_number + "\", \"Faculty\": " + getFaculty() + ", \"Level\": " + level + "}";
	}

	public int getStudentIdNumber() {
		return student_id_number;
	}

	public void setStudentIdNumber(int student_id_number) {
		this.student_id_number = student_id_number;
	}

	public String getFaculty() {
		return faculty;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public boolean canBorrow() {
		return borrowCount < 5;
	}

}
