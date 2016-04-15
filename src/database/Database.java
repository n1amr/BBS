package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
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

	static final String getEntryFilename(DatabaseTable databaseTable, int id) {
		return getTablePath(databaseTable) + "/" + id + ".dat";
	}

	public int commit(DatabaseTable databaseTable, RawEntry rawEntry) throws FileNotFoundException {
		boolean newEntry = (rawEntry.getId() == -1);
		if (newEntry) {
			rawEntry.setId(getLastEntryID(databaseTable) + 1);
		}

		PrintWriter out = new PrintWriter(getEntryFilename(databaseTable, rawEntry.getId()));

		for (String line : rawEntry.getData()) {
			out.println(line);
		}

		out.flush();
		out.close();

		return rawEntry.getId();
	}

	private int getLastEntryID(DatabaseTable databaseTable) {
		// TODO Auto-generated method stub
		return 0;
	}

	int getEntryId(File entryFile) {
		int id = -1;
		Pattern pattern = Pattern.compile(".*/(\\d+)\\.dat");
		Matcher matcher = pattern.matcher(entryFile.getName());

		if (matcher.find())
			id = Integer.valueOf(matcher.group(1));

		return id;
	}

	public RawEntry load(DatabaseTable databaseTable, int id) throws FileNotFoundException {
		return load(new File(getEntryFilename(databaseTable, id)));
	}

	RawEntry load(File entryFile) throws FileNotFoundException {
		int id = getEntryId(entryFile);
		RawEntry result = new RawEntry(id, new ArrayList<String>());

		Scanner in = new Scanner(entryFile);

		while (in.hasNextLine())
			result.getData().add(in.nextLine());

		in.close();

		return result;
	}

	public ArrayList<RawEntry> loadAll(DatabaseTable databaseTable) throws FileNotFoundException {
		ArrayList<RawEntry> entries = new ArrayList<RawEntry>();

		File tableDirectory = new File(getTablePath(databaseTable));

		for (File entryFile : tableDirectory.listFiles()) {
			RawEntry entry = load(entryFile);
			entries.add(entry);
		}

		return entries;
	}

	public boolean removeEntry(DatabaseTable databaseTable, int id) {
		File entryFile = new File(getEntryFilename(databaseTable, id));
		return entryFile.delete();
	}

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Hello");
		ArrayList<String> data = new ArrayList<>();
		data.add("amr");
		data.add("pass");
		RawEntry rawEntry = new RawEntry(2, data);
		Database.getSingleton().commit(DatabaseTable.BOOK, rawEntry);
	}
}
