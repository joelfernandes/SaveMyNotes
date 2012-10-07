package pt.test.model.dal;

import pt.test.utils.Log;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "notes.db";
	private static final int DATABASE_VERSION = 1;
	
	// ------ TABLE NOTES ------
	public static final String TABLE_NOTES = "notes";
	public static final String NOTE_ID = "_id";
	public static final String NOTE_TEXT = "note_text";
	public static final String NOTE_CATEGORY = "category";
	
	public static final String [] NOTE_ALL_COLUMNS = {NOTE_ID, NOTE_TEXT, NOTE_CATEGORY};
	
	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table " + TABLE_NOTES + "(" 
			+ NOTE_ID + " integer primary key autoincrement, " 
			+ NOTE_TEXT + " text not null," 
			+ NOTE_CATEGORY + " text not null"
			+ ");";
	
	public DbOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w("Upgrading database from version " + oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
		onCreate(db);
	}
	
}
