package database.models;

import java.io.IOException;
import java.util.ArrayList;

import database.RawEntry;

public interface Model {
	public RawEntry toRawEntry();

	public int commit() throws IOException;

	public void remove();

	public int getId();

	@Override
	public String toString();

}
