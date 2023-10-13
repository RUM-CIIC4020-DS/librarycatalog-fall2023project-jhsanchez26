package main;

import interfaces.List;

/**
 * This class implements a user with unique ID, name, and which books they have checked out.
 * It also calculates if they have any late fees.
 */
public class User {
	private int id;
	private String name;
	private List<Book> checkedOutList;
	
	/**
	 * gets the ID of the user.
	 * @return ID of the user.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets desired ID for a user.
	 * @param id to set for user.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * gets the name of the user.
	 * @return name of user.
	 */
	public String getName() {
		return name;
	}

	/**
	 * sets the name of the user.
	 * @param name to set for user.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the list of books checked out by the user.
	 * @return List of books checked out by the user.
	 */
	public List<Book> getCheckedOutList() {
		return checkedOutList;
	}

	/**
	 * Set the list of books checked out by the user.
	 * @param checkedOutList of books to set for the user.
	 */
	public void setCheckedOutList(List<Book> checkedOutList) {
		this.checkedOutList = checkedOutList;
	}
	
	/**
	 * Calculates the late fees owed by the user.
	 * @return fees owed by the user.
	 */
	public float calculateTotalFees() {
		float fees = 0;
		for (Book book : checkedOutList) {
			if (book.isCheckedOut() && book.calculateFees()>0) {
				fees+=book.calculateFees();
			}
		}
		return fees;
	}
	
}
