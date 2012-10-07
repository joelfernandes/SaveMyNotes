package pt.test.notes;

import java.util.List;

import pt.test.model.Note;
import pt.test.model.dal.NotesDAO;
import pt.test.utils.Log;
import pt.test.utils.Str;
import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SaveMyNotesActivity extends Activity {
	private static final int CONTEXT_MENU_EDIT = 0;
	private static final int CONTEXT_MENU_DELETE = 1;
	
	private NotesDAO datasource;
	private ListView listView;
	private NotesRowAdapter notesAdapter;
	private EditText editTextMessage;
	private Spinner spinnerCategories;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Log.Tag = "SaveMyNotes";
		
		datasource = new NotesDAO(SaveMyNotesActivity.this);
		datasource.open();
		
		editTextMessage = (EditText) findViewById(R.id.etNoteText);
		spinnerCategories = (Spinner)findViewById(R.id.spinnerCategories);
		listView = (ListView) findViewById(R.id.list_view_notes);
		
		List<Note> notes = datasource.getAllNotes();
		Log.d("Found " + notes.size() + " note(s) in database");
		
		notesAdapter = new NotesRowAdapter(SaveMyNotesActivity.this, notes);
		listView.setAdapter(notesAdapter);
		
		/* This code is used to set the empty view */
		View empty = getLayoutInflater().inflate(R.layout.list_empty, null);
		((ViewGroup) listView.getParent()).addView(empty);
		((TextView) findViewById(R.id.list_empty_message)).setText("No notes!\nWhy don't you add one?");
		listView.setEmptyView(empty);
		registerForContextMenu(listView);
		
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btSave:
				
				// is it a insert or delete?
				Note tagNote = (Note) editTextMessage.getTag();
				
				String message = editTextMessage.getText().toString();
				if (Str.isBlank(message)) {
					Toast.makeText(SaveMyNotesActivity.this, "Please specify a text", Toast.LENGTH_SHORT).show();
					return;
				}
				String category = spinnerCategories.getSelectedItem().toString();
				
				
				//insert
				if(tagNote == null) {	
					Note inserted = datasource.createNote(message, category);
					if (inserted == null) {
						Toast.makeText(SaveMyNotesActivity.this, "Failed to insert note in database!", Toast.LENGTH_SHORT)
								.show();
						return;
					}
					
					notesAdapter.addItem(inserted);
				
				// edit
				} else {
					Note editing = (Note) 
							notesAdapter.getItem(notesAdapter.getNotePosition(tagNote));
					editing.setText(message);
					editing.setCategory(category);
					editing.setId(tagNote.getId());
					
					if(datasource.updateNote(editing)) {
						notesAdapter.notifyDataSetChanged();
					}
					
					editTextMessage.setTag(null);
				}
				
				editTextMessage.setText("");
				break;
			
			default:
				Log.w("I'm afraid that " + v.getId() + " is not recognized as a valid ID for onClick");
				break;
		}
	}
	
	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}
	
	/* Menus, menus everywhere */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		if (v.getId() == R.id.list_view_notes) {
//			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
//			Note selected = (Note) notesAdapter.getItem(info.position);
			
			menu.setHeaderTitle(getString(R.string.ctx_menu_title));
			
			menu.add(Menu.NONE, CONTEXT_MENU_EDIT, CONTEXT_MENU_EDIT, 
					getString(R.string.ctx_menu_edit));
			menu.add(Menu.NONE, CONTEXT_MENU_DELETE, CONTEXT_MENU_DELETE, 
					getString(R.string.ctx_menu_delete));
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		Note note = (Note) notesAdapter.getItem(info.position);
		
		switch (item.getItemId()) {
			case CONTEXT_MENU_EDIT:
				editTextMessage.setTag(note);
				editTextMessage.setText(note.getText());
				
				SpinnerAdapter spinnerAdapter = spinnerCategories.getAdapter();
				int numCategories = spinnerAdapter.getCount();
				for (int i = 0; i < numCategories; i++) {
					String cat = spinnerAdapter.getItem(i).toString();
					if(cat.equals(note.getCategory())) {
						spinnerCategories.setSelection(i);
					}
				}
				break;
			
			case CONTEXT_MENU_DELETE:
				datasource.deleteNote(note);
				notesAdapter.removeItem(note);
				break;
				
			default:
				break;
		}
		
		return true;
	}
	
}