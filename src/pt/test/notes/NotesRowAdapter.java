package pt.test.notes;

import java.util.List;

import pt.test.model.Note;
import pt.test.utils.CollectionFinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NotesRowAdapter extends BaseAdapter {
	private Context mContext;
	private List<Note> noteList;
	
	public NotesRowAdapter(Context context, List<Note> noteList) {
		mContext = context;
		this.noteList = noteList;
	}
	
	public int getCount() {
		return noteList.size();
	}
	
	public Object getItem(int position) {
		return noteList.get(position);
	}
	
	public int getNotePosition(Note item) {
		return CollectionFinder.findObjectPosition(item, noteList);
	}
	
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		Note note = (Note) getItem(position);
		
		View ret = null;
		try {
			ret = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
			((TextView) ret.findViewById(R.id.list_item_title)).setText(note.getText());
			((TextView) ret.findViewById(R.id.list_item_subtitle)).setText(note.getCategory());
		} catch (Throwable ignore) { }
		
		return ret;
	}

	public void addItem(Note note) {
		this.noteList.add(note);
		notifyDataSetChanged();
	}
	
	public boolean removeItem(Note note) {
		if (noteList.contains(note)) {
			boolean removed = noteList.remove(note);
			if(removed) {
				notifyDataSetChanged();
			}
			return removed;
		}
		return false;
	}
	
	public List<Note> getAllItems() {
		return noteList;
	}
	
}
