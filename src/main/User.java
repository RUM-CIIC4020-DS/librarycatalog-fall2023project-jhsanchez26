package main;

import interfaces.List;

public class User {
	private int id;
	private String name;
	private List<Book> checkedOutList;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Book> getCheckedOutList() {
		return checkedOutList;
	}

	public void setCheckedOutList(List<Book> checkedOutList) {
		this.checkedOutList = checkedOutList;
	}
	
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
