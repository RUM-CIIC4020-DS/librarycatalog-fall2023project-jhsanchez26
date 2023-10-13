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
/**
* This class implements a library catalog with all of the users and books as well as some operations such as which books are checked out,
* which books are cataloged, what users are in the system, add a book to the catalog, remove a book from the catalog, checkout book,
* return book, check whether a book is in the catalog and available for checkout, how many books of the same title are in the catalog,
* generate a report of a summary of the library catalog, count of the books by genre,  show which books are checked out, write a report to
* the file, search for users, and search for books.
*/
public class LibraryCatalog {
	private List<Book> books;
	private List<User> users;
	private float totalFees;
		
	public LibraryCatalog() throws IOException {
		this.books = getBooksFromFiles();
		this.users = getUsersFromFiles();
	}
	
	/** 
	 * Reads a file to return a list of books.
	 * @return List of books from the file.
	 * @throws IOException If an error occurs while reading the file.
	 */
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
	
	/** 
	 * Reads a file to return a list of users.
	 * @return List of users from the file.
	 * @throws IOException If an error occurs while reading the file.
	 */
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
	
	/**
	 * Returns a list of checked out books based on IDs.
	 * @param checkedOutBooks String containing book IDs.
	 * @return List of checked out books.
	 */
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
	
	/**
	 * Gets all books in the catalog.
	 * @return List of all books in the catalog.
	 */
	public List<Book> getBookCatalog() {
		return books;
	}
	
	/**
	 * Gets all users in the catalog.
	 * @return List of all users in the catalog.
	 */
	public List<User> getUsers() {
		return users;
	}
	
	/**
	 * Adds a book to the catalog based on its title, author, and genre. Also sets checked out to false, last checkout day to September 15, 2023,
	 * and gives the book a unique ID based on the amount of books in the catalog and in what order they were added.
	 * @param title The title of the book.
	 * @param author The author of the book.
	 * @param genre The genre of the book.
	 */
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
	
	/**
	 * Removes a book from the catalog based on its unique ID.
	 * @param id The ID of the book to be removed.
	 */
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
	
	/**
	 * Sets a book as checked out and the date of which it was checked out.
	 * @param id The id of the book to be checked out.
	 * @return True if the book was successfully checked out. False if the book is not in the library or is already checked out.
	 */
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
	
	/**
	 * The opposite of checking out a book. When a book is returned, the lastCheckOut date is set as null because it is not checked out anymore.
	 * It is no longer checked out. 
	 * @param id The ID of the book to be returned.
	 * @return True if the book was successfully returned. False if the book doesn't belong to the library.
	 */
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
	
	/**
	 * Shows whether or not a book is available for checkout.
	 * @param id The ID of the book to be checked.
	 * @return True if the book can be checked out. False if the book is not in the library or is already checked out.
	 */
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
	
	/**
	 * Shows the amount of books with the same title.
	 * @param title The title of the book to be counted.
	 * @return The amount of books with the desired title.
	 */
	public int bookCount(String title) {
		int count = 0;
		for (Book book : books) {
			if (book.getTitle().equalsIgnoreCase(title)) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Creates a report of the books as well as the users with late fees.
	 * @throws IOException If an error occurs while writing the report.
	 */
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
				output += user.getName() + "\t\t\t\t\t$" + fees + "\n";
				totalFees += fees;
			}
		}

			
		output += "====================================================\n";
		output += "\t\t\t\tTOTAL DUE\t$" + totalFees + "\n\n\n";
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
	
	/**
	 * Shows the amount of books with the desired genre.
	 * @param genre The genre to be counted.
	 * @return Amount of books of the desired genre.
	 */
	private int bookCountByGenre(String genre) {
		int count = 0;
		for (Book book : books) {
			if (book.getGenre().equalsIgnoreCase(genre)) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Shows which books are currently checked out.
	 * @param output The output to append title and author of checked out books.
	 * @return The amount of books which are checked out.
	 */
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
	
	/**
	 * Create a file with the report of books and users.
	 * @param report The report to be added to the file.
	 * @throws IOException If there is an error while writing the file.
	 */
	private void writeReportToFile(String report) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("library_report.txt"))){
			writer.write(report);
		}
	}
	
	/**
	 * Searches for users.
	 * @param func Filter to apply to users.
	 * @return List of users found.
	 */
	public List<User> searchForUsers(FilterFunction<User> func){
		List<User> result = new ArrayList<>();
		for (User user : users) {
			if (func.filter(user)) {
				result.add(user);
			}
		}
		return result;
	}
	
	/**
	 * Searches for books.
	 * @param func Filter to apply to books.
	 * @return List of books found.
	 */
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
