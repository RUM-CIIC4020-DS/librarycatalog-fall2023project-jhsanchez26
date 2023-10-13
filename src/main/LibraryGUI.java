package main;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * This class creates a user interface for the library catalog. It implements methods such as adding books, removing books, and displaying
 * the books that are in the catalog.
 */
public class LibraryGUI extends JFrame {
	private LibraryCatalog libraryCatalog;
	private JTextField titleField;
	private JTextField authorField;
	private JTextField genreField;
	private JTextArea result;
	/**
	 * This function creates the window and the layout of the user interface.
	 */
	public LibraryGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		setVisible(true);
		try {
			libraryCatalog = new LibraryCatalog();
		} catch (IOException e){
			e.printStackTrace();
		}
		
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		titleField = new JTextField();
		authorField = new JTextField();
		genreField = new JTextField();
		add(new JLabel("Title:"));
		add(titleField);
		add(new JLabel("Author:"));
		add(authorField);
		add(new JLabel("Genre:"));
		add(genreField);
		add(addButton("Add Book",e->addBook()));
		add(addButton("Remove Book",e->removeBook()));
		add(addButton("Display Books",e->displayBooks()));
		
		
		result = new JTextArea();
		result.setEditable(false);
		add(result);
	}
	
	/**
	 * This function is used to add buttons to the user interface.
	 * @param text is used to display what the button does.
	 * @param listener is used to determine when the button is pressed.
	 * @return the button itself.
	 */
	private JButton addButton(String text, ActionListener listener) {
		JButton button = new JButton(text);
		button.addActionListener(listener);
		return button;
	}
	
	/**
	 * This function is used to add a book to the catalog. It needs the title, author, and genre.
	 */
	private void addBook() {
		String title = titleField.getText();
		String author = authorField.getText();
		String genre = genreField.getText();
		libraryCatalog.addBook(title, author, genre);
		titleField.setText("");
        authorField.setText("");
        genreField.setText("");
	}
	
	/**
	 * This function uses the book ID to remove it from the catalog.
	 */
	private void removeBook() {
		String bookId = JOptionPane.showInputDialog(this, "Enter Book ID:");
		int id = Integer.parseInt(bookId);
		libraryCatalog.removeBook(id);
	}
	
	/**
	 * This function displays all books in the catalog.
	 */
	private void displayBooks() {
		result.setText("");
		for (Book book : libraryCatalog.getBookCatalog()) {
			result.append(book.toString()+"\n");
		}
	}
	
	/**
	 * This is the main function which calls upon the function that creates the window for the user interface.
	 * @param args is not used in this program.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new LibraryGUI());
	}
}