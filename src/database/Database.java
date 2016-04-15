package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Database {
	static final String DATABASE_PATH = "database";

	private static Database singleton = null;

	private Database() {

	}

	public static Database getSingleton() {
		if (singleton == null) {
			singleton = new Database();
			checkFiles();
		}

		return singleton;
	}

	static void checkFiles() {
		File dir;

		dir = new File(getTablePath(DatabaseTable.BOOK));
		if (!dir.exists())
			dir.mkdirs();
		dir = new File(getTablePath(DatabaseTable.BORROWING));
		if (!dir.exists())
			dir.mkdirs();
		dir = new File(getTablePath(DatabaseTable.BORROWER));
		if (!dir.exists())
			dir.mkdirs();
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

	public int commit(DatabaseTable databaseTable, RawEntry rawEntry) throws IOException {
		boolean newEntry = rawEntry.getId() == -1;
		if (newEntry)
			rawEntry.setId(getLastEntryID(databaseTable) + 1);

		PrintWriter out = new PrintWriter(getEntryFilename(databaseTable, rawEntry.getId()));

		for (String line : rawEntry.getData())
			out.println(line);

		out.flush();
		out.close();

		// Update last id value
		if (newEntry) {
			out = new PrintWriter(getTableInfoFilename(databaseTable));
			out.println(rawEntry.getId());
			out.flush();
			out.close();
		}

		return rawEntry.getId();
	}

	private int getLastEntryID(DatabaseTable databaseTable) throws IOException {
		File file = new File(getTableInfoFilename(databaseTable));
		if (!file.exists()) {
			file.createNewFile();
			PrintWriter out = new PrintWriter(file);
			out.println("-1");
			out.flush();
			out.close();
		}

		Scanner in = new Scanner(file);

		int id = -1;
		if (in.hasNextInt())
			id = in.nextInt();

		in.close();

		return id;
	}

	int getEntryID(File entryFile) {
		int id = -1;
		Pattern pattern = Pattern.compile("(\\d+)\\.dat$");
		Matcher matcher = pattern.matcher(entryFile.getName());

		if (matcher.find())
			id = Integer.valueOf(matcher.group(1));

		return id;
	}

	public RawEntry load(DatabaseTable databaseTable, int id) throws FileNotFoundException {
		return load(new File(getEntryFilename(databaseTable, id)));
	}

	RawEntry load(File entryFile) throws FileNotFoundException {
		int id = getEntryID(entryFile);
		if (id == -1)
			return null;

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
			if (entry != null)
				entries.add(entry);
		}

		return entries;
	}

	public boolean removeEntry(DatabaseTable databaseTable, int id) {
		File entryFile = new File(getEntryFilename(databaseTable, id));
		return entryFile.delete();
	}
}
