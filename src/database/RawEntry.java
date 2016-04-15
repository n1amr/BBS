package database;

import java.util.ArrayList;

public class RawEntry {
	private int id;
	private ArrayList<String> data;

	public RawEntry(int id, ArrayList<String> data) {
		this.id = id;
		this.data = data;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<String> getData() {
		return data;
	}

	public void setData(ArrayList<String> data) {
		this.data = data;
	}
}
