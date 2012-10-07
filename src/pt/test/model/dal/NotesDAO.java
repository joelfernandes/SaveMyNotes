package pt.test.model.dal;

import java.util.ArrayList;
import java.util.List;

import pt.test.model.Note;
import pt.test.utils.Log;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class NotesDAO {
	// Database fields
	private SQLiteDatabase database;
	private DbOpenHelper dbHelper;
	private String[] allColumns = DbOpenHelper.NOTE_ALL_COLUMNS;
	
	public NotesDAO(Context context) {
		dbHelper = new DbOpenHelper(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public Note createNote(String text, String category) {
		ContentValues values = new ContentValues();
		values.put(DbOpenHelper.NOTE_TEXT, text);
		values.put(DbOpenHelper.NOTE_CATEGORY, category);
		
		long insertId = database.insert(DbOpenHelper.TABLE_NOTES, null, values);
		
		if(insertId == -1) {
			Log.w("Failed to insert note [" + text + "," + category + " in table " 
					+ DbOpenHelper.TABLE_NOTES);
			return null;
		}
		
		Cursor cursor = database.query(DbOpenHelper.TABLE_NOTES, allColumns, DbOpenHelper.NOTE_ID + " = " + insertId,
				null, null, null, null);
		cursor.moveToFirst();
		Note newNote = cursorToNote(cursor);
		cursor.close();
		return newNote;
	}
	
	public void deleteNote(Note note) {
		long id = note.getId(); 
		Log.d("Note deleted with id: " + id);
		database.delete(DbOpenHelper.TABLE_NOTES, DbOpenHelper.NOTE_ID + " = " + id, null);
	}
	
	public boolean updateNote(Note note) {
		ContentValues values = new ContentValues();
		values.put(DbOpenHelper.NOTE_ID, note.getId());
		values.put(DbOpenHelper.NOTE_TEXT, note.getText());
		values.put(DbOpenHelper.NOTE_CATEGORY, note.getCategory());
		
		int rowsAffected = database.update(DbOpenHelper.TABLE_NOTES, values, 
				DbOpenHelper.NOTE_ID + " = " + note.getId(), null);
		
		return rowsAffected > 0;
	}
	
	public List<Note> getAllNotes() {
		List<Note> notes = new ArrayList<Note>();
		
		Cursor cursor = database.query(DbOpenHelper.TABLE_NOTES, allColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Note note = cursorToNote(cursor);
			notes.add(note);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return notes;
	}
	
	private Note cursorToNote(Cursor cursor) {
		Note note = new Note();
		note.setId(cursor.getLong(0));
		note.setText(cursor.getString(1));
		note.setCategory(cursor.getString(2));
		return note;
	}
	
}
