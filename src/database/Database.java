package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Database {
	static final String DATABASE_PATH = "database";

	private static Database singleton = null;

	private Database() {

	}

	public static Database getSingleton() {
		if (singleton == null)
			singleton = new Database();
		return singleton;
	}

	static final String getTablePath(DatabaseTable databaseTable) {
		return DATABASE_PATH + "/" + databaseTable.getName();
	}

	static final String getTableInfoFilename(DatabaseTable databaseTable) {
		return getTablePath(databaseTable) + "/info";
	}

	static final String getRecordFilename(DatabaseTable databaseTable, int id) {
		return getTablePath(databaseTable) + "/" + id + ".dat";
	}

	int store(DatabaseTable databaseTable, int id, String[] data) throws FileNotFoundException {
		boolean newEntry = (id == -1);
		if (newEntry) {
			id = getLastRecordID(databaseTable) + 1;
		}

		PrintWriter out = new PrintWriter(getRecordFilename(databaseTable, id));

		for (String line : data) {
			out.println(line);
		}

		out.flush();
		out.close();

		return 0;
	}

	private int getLastRecordID(DatabaseTable databaseTable) {
		// TODO Auto-generated method stub
		return 0;
	}

	String[] load(DatabaseTable databaseTable, int id) throws FileNotFoundException {
		return load(new File(getRecordFilename(databaseTable, id)));
	}

	String[] load(File recordFile) throws FileNotFoundException {
		String[] result;

		Scanner in = new Scanner(recordFile);

		LinkedList<String> lines = new LinkedList<String>();
		while (in.hasNextLine())
			lines.add(in.nextLine());

		in.close();
		
		int size = lines.size();
		result = new String[size];
		int i = 0;
		for (String line : lines) {
			result[i] = line;
			i++;
		}
		return result;
	}

	String[][] loadAll(DatabaseTable databaseTable) {
		// TODO
		File tableDirectory = new File(getTablePath(databaseTable));
		Pattern pattern = Pattern.compile("(\\d+)\\.dat");
		Matcher matcher;
		String filename;
		int id = -1;

		Scanner in;
		for (File recordFile : tableDirectory.listFiles()) {
			filename = recordFile.getName();
			matcher = pattern.matcher(filename);

			if (matcher.find()) {
				id = Integer.valueOf(matcher.group(1));
			}

		}
		System.out.println(tableDirectory.getAbsolutePath());
		return null;
	}

	void removeRecord(DatabaseTable databaseTable, int id) {
		// TODO
	}

	int get_last_id(DatabaseTable databaseTable) {
		// TODO
		return 0;
	}

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Hello");
		Database.getSingleton().store(DatabaseTable.BOOK, 2, new String[] { "amr", "pass" });
		Database.getSingleton().loadAll(DatabaseTable.BOOK);
	}

}
