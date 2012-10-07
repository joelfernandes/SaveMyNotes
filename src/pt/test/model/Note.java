package pt.test.model;

public class Note {
	public static final int NO_ID = -1;
	
	private long id = NO_ID;
	private String text;
	private String category;

	public Note() {}
	
	public Note(String text, String category) {
		this.category = category;
		this.text = text;
	}
	
	public Note(String text, String category, long id) {
		this.category = category;
		this.text = text;
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		
		if((obj instanceof Note) == false) {
			return false;
		}
		
		return ((Note)obj).getId() == this.getId();
		
	}
	
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}


	
}
