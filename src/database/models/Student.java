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
	private int faculty_id;
	private int level;

	public Student() {
		super();
	}

	public Student(Name name, String ssn, int borrowCount, int student_id_number, int faculty_id, int level) {
		super(name, ssn, borrowCount);
		this.student_id_number = student_id_number;
		this.faculty_id = faculty_id;
		this.level = level;
	}

	public Student(Name name, String ssn, int borrowCount, int student_id_number, Faculty faculty, int level) {
		this(name, ssn, borrowCount, student_id_number, faculty.getId(), level);
	}

	public static Student load(int id) throws FileNotFoundException {
		RawEntry rawEntry = Database.getSingleton().load(DatabaseTable.BORROWER, id);

		Student student = new Student(rawEntry);
		return student;
	}

	public Student(RawEntry rawEntry) {
		super(rawEntry);

		student_id_number = Integer.valueOf(rawEntry.getData().get(5));
		faculty_id = Integer.valueOf(rawEntry.getData().get(6));
		level = Integer.valueOf(rawEntry.getData().get(7));
	}

	@Override
	public RawEntry toRawEntry() {
		RawEntry rawEntry = super.toRawEntry();
		ArrayList<String> data = rawEntry.getData();

		data.add(Borrower.TYPE_STUDENT);
		data.add(Integer.toString(student_id_number));
		data.add(Integer.toString(faculty_id));
		data.add(Integer.toString(level));

		return rawEntry;
	}

	@Override
	public String toString() {
		try {
			return "{ \"id\": " + id + ", \"Name\": \"" + name + "\", \"SSN\": \"" + ssn + "\", \"IDNumber\": \"" + student_id_number + "\", \"Faculty\": " + getFaculty().toString() + ", \"Level\": " + level + "}";
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getStudentIdNumber() {
		return student_id_number;
	}

	public void setStudentIdNumber(int student_id_number) {
		this.student_id_number = student_id_number;
	}

	public int getFacultyId() {
		return faculty_id;
	}

	public Faculty getFaculty() throws FileNotFoundException {
		return Faculty.load(faculty_id);
	}

	public void setFacultyId(int faculty_id) {
		this.faculty_id = faculty_id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	public boolean canBorrow() {
		return borrowCount < 5;
	}

}
