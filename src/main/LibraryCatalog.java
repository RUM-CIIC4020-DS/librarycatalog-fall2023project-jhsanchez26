package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import data_structures.ArrayList;
import data_structures.DoublyLinkedList;
import data_structures.SinglyLinkedList;
import interfaces.FilterFunction;
import interfaces.List;

public class LibraryCatalog {
	private List<Book> books;
	private List<User> users;
	private float totalFees;
		
	public LibraryCatalog() throws IOException {
		this.books = getBooksFromFiles();
		this.users = getUsersFromFiles();
	}
	private List<Book> getBooksFromFiles() throws IOException {
		List<Book> bookList = new ArrayList<>();
		
		BufferedReader reader = new BufferedReader(new FileReader("data/catalog.csv"));
		reader.readLine();
		String line;
		
		while ((line = reader.readLine())!= null) {
			String[] bookInfo = line.split(",");
			int id = Integer.parseInt(bookInfo[0].trim());
			String title = bookInfo[1].trim();
			String author = bookInfo[2].trim();
			String genre = bookInfo[3].trim();
			LocalDate lastCheckOut = LocalDate.parse(bookInfo[4].trim());
			boolean checkedOut = Boolean.parseBoolean(bookInfo[5].trim());
			Book book = new Book();
			book.setId(id);
			book.setTitle(title);
			book.setAuthor(author);
			book.setGenre(genre);
			book.setLastCheckOut(lastCheckOut);
			book.setCheckedOut(checkedOut);
			bookList.add(book);
		}
		reader.close();
		return bookList;
	}
	
	private List<User> getUsersFromFiles() throws IOException {
		List<User> userList = new ArrayList<>();
		List<Book> checkedOutList;
		BufferedReader reader = new BufferedReader(new FileReader("data/user.csv"));
		reader.readLine();
		String line;
		while ((line=reader.readLine())!=null) {
			String[] userInfo = line.split(",");
			int id = Integer.parseInt(userInfo[0].trim());
			String name = userInfo[1].trim();
			if (userInfo.length==3) {
				checkedOutList = getCheckedOutList(userInfo[2].replaceAll("\\s", "").replace("{", "").replace("}", "").trim());
			} else {
				checkedOutList = new ArrayList<>();
			}
			User user = new User();
			user.setId(id);
			user.setName(name);
			user.setCheckedOutList(checkedOutList);
			userList.add(user);
		}
		reader.close();
		return userList;
	}
	
	public List<Book> getCheckedOutList(String checkedOutBooks){
		List<Book> checkedOutList = new ArrayList<>();
		String[] bookIDs = checkedOutBooks.split(",");
		List<Book> allBooks = getBookCatalog();
		for (String bookID : bookIDs) {
			int id = Integer.parseInt(bookID.trim());
			for (Book b : allBooks) {
				if (b.getId() == id) {
					checkedOutList.add(b);
					break;
				}
			}
		}
		return checkedOutList;
	}
	
	
	public List<Book> getBookCatalog() {
		return books;
	}
	public List<User> getUsers() {
		return users;
	}
	public void addBook(String title, String author, String genre) {
		Book book = new Book();
		book.setTitle(title);
		book.setAuthor(author);
		book.setGenre(genre);
		book.setCheckedOut(false);
		book.setLastCheckOut(LocalDate.of(2023, 9, 15));
		book.setId(books.size()+1);
		books.add(book);
	}
	
	public void removeBook(int id) {
		int index = -1;
		for (int i = 0; i < books.size(); i++) {
			if (books.get(i).getId() == id) {
				index = i;
				break;
			}
		}
		if (index != -1) {
			books.remove(index);
		}
	}	
	
	public boolean checkOutBook(int id) {
		Book b = null;
		for (Book book : books) {
			if (book.getId()==id) {
				b = book;
				break;
			}
		}
		if (b != null && !b.isCheckedOut()) {
			b.setCheckedOut(true);
			b.setLastCheckOut(LocalDate.now());
			return true;
		} else {
			return false;
		}
	}
	public boolean returnBook(int id) {
		Book b = null;
		for (Book book : books) {
			if (book.getId() == id && book.isCheckedOut()) {
				b = book;
				break;
			}
		}
		if (b != null) {
			b.setCheckedOut(false);
			b.setLastCheckOut(null);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean getBookAvailability(int id) {
		Book b = null;
		for (Book book : books) {
			if (book.getId() == id) {
				b = book;
				break;
			}
		}
		return (b != null) && (!b.isCheckedOut());
	}
	public int bookCount(String title) {
		int count = 0;
		for (Book book : books) {
			if (book.getTitle().equalsIgnoreCase(title)) {
				count++;
			}
		}
		return count;
	}
	public void generateReport() throws IOException {
		totalFees = 0;
		String output = "\t\t\t\tREPORT\n\n";
		output += "\t\tSUMMARY OF BOOKS\n";
		output += "GENRE\t\t\t\t\t\tAMOUNT\n";
		/*
		 * In this section you will print the amount of books per category.
		 * 
		 * Place in each parenthesis the specified count. 
		 * 
		 * Note this is NOT a fixed number, you have to calculate it because depending on the 
		 * input data we use the numbers will differ.
		 * 
		 * How you do the count is up to you. You can make a method, use the searchForBooks()
		 * function or just do the count right here.
		 */
		output += "Adventure\t\t\t\t\t" + bookCountByGenre("Adventure") + "\n";
		output += "Fiction\t\t\t\t\t\t" + bookCountByGenre("Fiction") + "\n";
		output += "Classics\t\t\t\t\t" + bookCountByGenre("Classics") + "\n";
		output += "Mystery\t\t\t\t\t\t" + bookCountByGenre("Mystery") + "\n";
		output += "Science Fiction\t\t\t\t\t" + bookCountByGenre("Science Fiction") + "\n";
		output += "====================================================\n";
		output += "\t\t\tTOTAL AMOUNT OF BOOKS\t" + books.size() + "\n\n";
		
		/*
		 * This part prints the books that are currently checked out
		 */
		output += "\t\t\tBOOKS CURRENTLY CHECKED OUT\n\n";
		/*
		 * Here you will print each individual book that is checked out.
		 * 
		 * Remember that the book has a toString() method. 
		 * Notice if it was implemented correctly it should print the books in the 
		 * expected format.
		 * 
		 * PLACE CODE HERE
		 */
		for (Book book : books) {
			if (book.isCheckedOut()) {
				output += book.toString()+"\n";
			}
		}
		
		
		output += "====================================================\n";
		output += "\t\t\tTOTAL AMOUNT OF BOOKS\t" + printCheckedOutBooks(output) + "\n\n";
		
		
		/*
		 * Here we will print the users the owe money.
		 */
		output += "\n\n\t\tUSERS THAT OWE BOOK FEES\n\n";
		/*
		 * Here you will print all the users that owe money.
		 * The amount will be calculating taking into account 
		 * all the books that have late fees.
		 * 
		 * For example if user Jane Doe has 3 books and 2 of them have late fees.
		 * Say book 1 has $10 in fees and book 2 has $78 in fees.
		 * 
		 * You would print: Jane Doe\t\t\t\t\t$88.00
		 * 
		 * Notice that we place 5 tabs between the name and fee and 
		 * the fee should have 2 decimal places.
		 * 
		 * PLACE CODE HERE!
		 */
		for (User user : users) {
			float fees = user.calculateTotalFees();
			if (fees>0) {
				output += user.getName() + "\t\t\t\t\t$" + String.format("%.2f", fees) + "\n";
				totalFees += fees;
			}
		}

			
		output += "====================================================\n";
		output += "\t\t\t\tTOTAL DUE\t$" + String.format("%.2f",totalFees) + "\n\n\n";
		output += "\n\n";
		System.out.println(output);// You can use this for testing to see if the report is as expected.
		
		/*
		 * Here we will write to the file.
		 * 
		 * The variable output has all the content we need to write to the report file.
		 * 
		 * PLACE CODE HERE!!
		 */
		writeReportToFile(output);
	}
	private int bookCountByGenre(String genre) {
		int count = 0;
		for (Book book : books) {
			if (book.getGenre().equalsIgnoreCase(genre)) {
				count++;
			}
		}
		return count;
	}
	private int printCheckedOutBooks(String output) {
		int count = 0;
		for (Book book : books) {
			if (book.isCheckedOut()) {
				output += book.toString() + "\n";
				count++;
			}
		}
		return count;
	}
	private void writeReportToFile(String report) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("library_report.txt"))){
			writer.write(report);
		}
	}
	public List<User> searchForUsers(FilterFunction<User> func){
		List<User> result = new ArrayList<>();
		for (User user : users) {
			if (func.filter(user)) {
				result.add(user);
			}
		}
		return result;
	}
	public List<Book> searchForBook(FilterFunction<Book> func){
		List<Book> result = new ArrayList<>();
		for (Book book : books) {
			if (func.filter(book)) {
				result.add(book);
			}
		}
		return result;
	}
}
