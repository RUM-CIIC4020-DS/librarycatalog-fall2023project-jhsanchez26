package main;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Book {
	private int id;
	private String title;
	private String author;
	private String genre;
	private LocalDate lastCheckOut;
	private boolean checkedOut;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public LocalDate getLastCheckOut() {
		return lastCheckOut;
	}
	public void setLastCheckOut(LocalDate lastCheckOut) {
		this.lastCheckOut = lastCheckOut;
	}
	public boolean isCheckedOut() {
		return checkedOut;
	}
	public void setCheckedOut(boolean checkedOut) {
		this.checkedOut = checkedOut;
	}
	
	@Override
	public String toString() {
		/*
		 * This is supposed to follow the format
		 * 
		 * {TITLE} By {AUTHOR}
		 * 
		 * Both the title and author are in uppercase.
		 */
		if (title != null && author != null) {
			return title.toUpperCase() + " By " + author.toUpperCase();
		} else {
			return "Title or Author not available.";
		}
	}
	public float calculateFees() {
		/*
		 * fee (if applicable) = base fee + 1.5 per additional day
		 */
		LocalDate currentDate = LocalDate.of(2023,9,15);
		if (checkedOut && lastCheckOut != null) {
			long daysCheckedOut = ChronoUnit.DAYS.between(lastCheckOut,currentDate);
			if(daysCheckedOut >= 31) {
				return 10f+((daysCheckedOut-31)*1.5f);
			} else {
				return 0;
			}
		} return 0;
	}
}
