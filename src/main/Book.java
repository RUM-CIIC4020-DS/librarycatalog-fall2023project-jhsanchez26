package main;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
 /**
 * This class implements a book with a unique identification (ID), title, author, genre, when it was last checked out, and whether or not it is checked out currently.
 */
public class Book {
	private int id;
	private String title;
	private String author;
	private String genre;
	private LocalDate lastCheckOut;
	private boolean checkedOut;
	
	 /**
     * gets ID of a book.
     *
     * @return ID of a book.
     */
	public int getId() {
		return id;
	}
	
	 /**
     * Sets ID of a book.
     *
     * @param id ID to set for the book.
     */
	public void setId(int id) {
		this.id = id;
	}
	
	 /**
     * Gets title of a book.
     *
     * @return Title of book.
     */
	public String getTitle() {
		return title;
	}
	
	 /**
     * Sets title for book.
     *
     * @param title to set for book.
     */
	public void setTitle(String title) {
		this.title = title;
	}
	
	 /**
     * Gets author of a book.
     *
     * @return Author of a book.
     */
	public String getAuthor() {
		return author;
	}
	
	 /**
     * Sets author of a book.
     *
     * @param author to set author for book.
     */
	public void setAuthor(String author) {
		this.author = author;
	}
	
	 /**
     * Gets the genre of a book.
     *
     * @return Genre of book.
     */
	public String getGenre() {
		return genre;
	}
	
	 /**
     * Set genre for book.
     *
     * @param genre to set for book.
     */
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	 /**
     * Get date when the book was last checked out.
     *
     * @return Last checkout date of book.
     */
	public LocalDate getLastCheckOut() {
		return lastCheckOut;
	}
	
	 /**
     * Set the date when the book was last checked out.
     *
     * @param lastCheckOut Last checkout date for the book.
     */
	public void setLastCheckOut(LocalDate lastCheckOut) {
		this.lastCheckOut = lastCheckOut;
	}
	
	 /**
     * Checks if the book is currently checked out or not.
     *
     * @return True if the book is checked out, False if the book is not checked out.
     */
	public boolean isCheckedOut() {
		return checkedOut;
	}
	
	 /**
     * Set checkout status for book.
     *
     * @param checkedOut Set checkout status for the book.
     */
	public void setCheckedOut(boolean checkedOut) {
		this.checkedOut = checkedOut;
	}
	
	 /**
     * String representation of a book.
     *
     * @return String containing title and author of a book.
     */
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
			return title.trim().toUpperCase() + " BY " + author.trim().toUpperCase();
		} else {
			return "Title or Author not available.";
		}
	}
	
	 /**
     * Calculates late fees for a book.
     *
     * @return Late fees for the book.
     */
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
