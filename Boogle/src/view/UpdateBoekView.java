package view;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.AuteurModel;
import model.BibliotheekModel;
import model.BoekModel;
import model.BoekenkastModel;
import model.Database;
import model.GenreModel;

public class UpdateBoekView extends GridPane {
	private final Label selectISBNLabel, libraryLabel, bookCaseNrLabel, genreLabel, titleLabel, languageLabel,
			releaseDateLabel, intTitleLabel, descriptionLabel, authorLabel, errorLabel, updateLibraryLabel;
	private final TextField titleTextField, languageTextField, releaseDateTextField, intTitleTextField;
	private final Button saveButton, addAuthorTextFieldButton, getISBNFromLibraryButton, getBookCaseFromLibraryButton,
			getBookDataFromDb, clearLibraryButton, chooseImageButton;
	private final Text text, selectText, updateText;
	private final ComboBox<String> selectISBNCB, selectLibraryCB, bookCaseCB, genreCB, updateLibraryCB;
	private final Database db;
	private final TextArea ta;
	private int authorCount = 0;
	private final ComboBox<String>[] authors = new ComboBox[5];
	private String B64STRING;

	public UpdateBoekView(Pane mainPane) {
		// Instantiating objects
		db = new Database();

		// Instantiating labelobjects
		selectISBNLabel = new Label("Selecteer ISBN:");
		libraryLabel = new Label("Selecteer bibliotheek:");
		bookCaseNrLabel = new Label("Boekenkast:");
		titleLabel = new Label("Titel:");
		languageLabel = new Label("Taal:");
		releaseDateLabel = new Label("Datum uitgave:");
		intTitleLabel = new Label("Internationale titel:");
		genreLabel = new Label("Genre:");
		descriptionLabel = new Label("Beschrijving:");
		authorLabel = new Label("Auteur");
		errorLabel = new Label("Error, niet alle velden zijn (correct) ingevuld");
		updateLibraryLabel = new Label("Bibliotheek");

		// Instantiating buttonobjects
		saveButton = new Button("Opslaan");
		addAuthorTextFieldButton = new Button("+");
		getISBNFromLibraryButton = new Button("Haal boeken uit bibliotheek op");
		getBookCaseFromLibraryButton = new Button("Haal boekenkasten uit bibliotheek op");
		getBookDataFromDb = new Button("Haal gegevens van boek op");
		clearLibraryButton = new Button("Clear bibliotheekkeuze");
		chooseImageButton = new Button("Kies afbeelding");

		// Instantiating comboboxes
		selectISBNCB = new ComboBox<String>();
		selectLibraryCB = new ComboBox<String>();
		bookCaseCB = new ComboBox<String>();
		genreCB = new ComboBox<String>();
		updateLibraryCB = new ComboBox<String>();

		// Instantiating textfieldobjects
		titleTextField = new TextField();
		languageTextField = new TextField();
		releaseDateTextField = new TextField();
		intTitleTextField = new TextField();

		// Instantiating textobjects
		text = new Text("Boek aanpassen");
		selectText = new Text("Selecteer boek");
		updateText = new Text("Pas boekgegevens aan");

		// Instantiating textareaobjects
		ta = new TextArea();

		// Make-up for text and layout
		text.setStyle("-fx-font: 17 arial");
		setPadding(new Insets(5, 5, 5, 5));
		setHgap(5);
		setVgap(5);
		ta.setPrefWidth(150);

		// Place labelobjects with for loop
		Label[] labelObjects = { updateLibraryLabel, bookCaseNrLabel, genreLabel, titleLabel, languageLabel,
				releaseDateLabel, intTitleLabel, descriptionLabel, authorLabel };
		for (int i = 0; i < labelObjects.length; i++) {
			this.add(labelObjects[i], 0, i + 5);
		}

		// Place textfieldobjects with for loop
		TextField[] textFieldObjects = { titleTextField, languageTextField, releaseDateTextField, intTitleTextField };
		for (int i = 0; i < textFieldObjects.length; i++) {
			this.add(textFieldObjects[i], 1, i + 8);
		}

		// Place comboboxes
		this.add(selectISBNCB, 1, 2);
		this.add(selectLibraryCB, 1, 3);
		this.add(bookCaseCB, 1, 6);
		this.add(genreCB, 1, 7);
		this.add(updateLibraryCB, 1, 5);

		// Place buttonobject
		this.add(saveButton, 1, 19);
		this.add(addAuthorTextFieldButton, 2, 13);
		this.add(getISBNFromLibraryButton, 2, 3);
		this.add(getBookCaseFromLibraryButton, 2, 5);
		this.add(getBookDataFromDb, 2, 2);
		this.add(clearLibraryButton, 3, 3);
		this.add(chooseImageButton, 1, 18);

		// Place textobject
		this.add(text, 0, 0);
		this.add(selectText, 0, 1);
		this.add(updateText, 0, 4);

		// Place labelobject
		this.add(errorLabel, 1, 20);
		this.add(selectISBNLabel, 0, 2);
		this.add(libraryLabel, 0, 3);

		// Place textareas
		this.add(ta, 1, 12);

		// Fill comboboxes with initial data
		setLibraryCB();
		setGenreCB();
		setBookCB("");

		// Set onactionlistener for chooseImageButton
		chooseImageButton.setOnAction(event -> {
			// Create a filechooser
			FileChooser fileChooser = new FileChooser();

			// set filter so that you can select PNG images
			FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
			fileChooser.getExtensionFilters().addAll(extFilterPNG);
			// Open filechooser
			File file = fileChooser.showOpenDialog(null);

			try {
				// Read image into a bufferedImage
				BufferedImage bufferedImage = ImageIO.read(file);
				ByteArrayOutputStream s = new ByteArrayOutputStream();
				ImageIO.write(bufferedImage, "png", s);
				// Convert image into a bytearray
				byte[] byteImage = s.toByteArray();
				s.close();
				// encode bytearray to B64String
				String encodedImage = Base64.getEncoder().encodeToString(byteImage);

				B64STRING = encodedImage;

				// Catch readException
			} catch (IOException e) {
				e.printStackTrace();
			}

		});

		// Set onactionlistener for clearlibrarybutton
		clearLibraryButton.setOnAction(event -> {
			selectLibraryCB.getSelectionModel().clearSelection();
			setBookCB("");
		});

		// Set onactionlistener for getISBNFromLibraryButton
		getISBNFromLibraryButton.setOnAction(event -> {
			// Check if a library in libraryCombobox has been selected
			if (!selectLibraryCB.getSelectionModel().isEmpty()) {
				// Search for all books in the library with the libraryName and set the book
				// combobox accordingly
				String libraryName = selectLibraryCB.getValue().toString();
				setBookCB(libraryName);
				errorLabel.setText("Boeken zijn opgehaald uit bibliotheek " + libraryName);
			} else {
				errorLabel.setText("Error, er is geen bibliotheek gekozen");
			}
		});

		// Set onactionlistener for getBookCaseFromLibraryButton
		getBookCaseFromLibraryButton.setOnAction(event -> {
			// Check if a library in libraryCombobox has been selected
			if (!updateLibraryCB.getSelectionModel().isEmpty()) {
				// Search for all bookCases in the library with the libraryName and set the
				// bookCase combobox accordingly
				String libraryName = updateLibraryCB.getValue().toString();
				setBookCaseCB(libraryName);
				errorLabel.setText("Boekenkasten zijn opgehaald uit bibliotheek " + libraryName);
			}
		});

		// Set onactionlistener for saveButton
		saveButton.setOnAction(event -> {
			// Run updatebook and check if the update failed with a returned int
			if ((updateBook() == 0)) {
				errorLabel.setText("Error, niet alle velden zijn correct ingevuld");
			} else {
				errorLabel.setText("Data is opgeslagen");
			}
		});

		// Set onactionlistener for getBookDataFromDb
		getBookDataFromDb.setOnAction(event -> {
			// Check if a ISBN in ISBN combobox has been selected
			if (!selectISBNCB.getSelectionModel().isEmpty()) {
				// Set all authoritems and bookitems (textfields and comboboxes)
				setAuthorItems(selectISBNCB.getValue().toString());
				setBookItems();
				if (!updateLibraryCB.getSelectionModel().isEmpty()) {
					String libraryName = updateLibraryCB.getValue().toString();
					setBookCaseCB(libraryName);
				}
				errorLabel.setText("Data is opgehaald");
			} else {
				errorLabel.setText("Error, er is geen ISBN geselecteerd");
			}
		});

		// set onactionlistener for addAuthorTextFieldButton
		addAuthorTextFieldButton.setOnAction(event -> {
			// Check if the number of authorComboboxes is smaller than 5
			if (authorCount < 5) {
				// Add a new authorCombobox
				authors[authorCount] = new ComboBox<String>();
				this.add(authors[authorCount], 1, authorCount + 13);

				// Set the data in the new author combobox
				authors[authorCount].getItems().clear();
				for (AuteurModel author : db.getAllAuthors()) {
					authors[authorCount].getItems().add(author.getName());

				}
				authorCount++;
			} else {
				errorLabel.setText("Error, je kunt niet meer dan 5 auteurs hebben");
			}

		});

		// Add all elements to the mainpane
		mainPane.getChildren().add(this);
	}

	/**
	 * Method that sets value of combobox from data of chosen book
	 * 
	 * @param String iSBN
	 */
	private void setAuthorItems(String iSBN) {
		int i = 0;
		// Check if there are any authors coupled to a book
		if (db.getAllAuthorsFromBook(iSBN) != null) {
			// Create authorcomboboxes if needed and fill with data
			while (i < db.getAllAuthorsFromBook(iSBN).size()) {
				if (i >= authorCount) {
					// Create authorcombobox
					authors[authorCount] = new ComboBox<String>();
					this.add(authors[authorCount], 1, authorCount + 13);
					// For loop that fills the new authorcombobox with data
					for (AuteurModel author : db.getAllAuthors()) {
						authors[authorCount].getItems().add(author.getName());
					}
					authorCount++;
				}
				i++;
			}

			int p = 0;
			while (p < i) {
				// Set the selectedvalue of authorcombobox
				for (AuteurModel author : db.getAllAuthorsFromBook(iSBN)) {
					authors[p].setValue(author.getName());
					p++;
				}
			}
		}
		// Set the rest of the authorcomboboxes selected values to null
		while (i < authorCount) {
			authors[i].valueProperty().set(null);
			i++;
		}
	}

	/**
	 * Method that sets the value of textfields and comboboxes from data of chosen
	 * book
	 */
	private void setBookItems() {
		BoekModel book = new BoekModel();
		BoekenkastModel bookCase = new BoekenkastModel();
		try {
			// Get bookmodel from database
			book = db.getBookFromISBN(selectISBNCB.getValue().toString());
		} catch (NullPointerException e) {
			errorLabel.setText("Error, er is geen ISBN geselecteerd om aan te passen");
		}

		try {
			// Get bookcaseModel from database
			bookCase = db.getBookcaseFromISBN(selectISBNCB.getValue().toString(),
					selectLibraryCB.getValue().toString());
		} catch (NullPointerException e) {
		}

		// Set all items for the bookdata
		genreCB.setValue(book.getGenre());
		titleTextField.setText(book.getTitle());
		languageTextField.setText(book.getLanguage());
		releaseDateTextField.setText(book.getReleaseDate());
		intTitleTextField.setText(book.getIntTitle());
		ta.setText(book.getDescription());

		// Check if the selectLibrary combobox is not emty
		if (!selectLibraryCB.getSelectionModel().isEmpty()) {
			// Set the library and bookcase comboboxvalue
			String libraryName = selectLibraryCB.getValue().toString();
			updateLibraryCB.setValue(libraryName);
			String bookCaseString = bookCase.getBookCaseNr() + "";
			//bookCaseCB.getSelectionModel().clearSelection();
			bookCaseCB.setValue(bookCaseString);
		} else {
			updateLibraryCB.getSelectionModel().clearSelection();
			//bookCaseCB.getSelectionModel().clearSelection();
		}
	}

	/**
	 * Method that fills the bookcase Combobox based on chosen library
	 * 
	 * @param name
	 */
	private void setBookCaseCB(String name) {
		bookCaseCB.getItems().clear();
		// Fill bookcaseCombobox with data
		for (BoekenkastModel bookCase : db.getAllBookCasesFromLibrary(name)) {
			String bookCaseNrString = bookCase.getBookCaseNr() + "";
			bookCaseCB.getItems().add(bookCaseNrString);
		}
	}

	/**
	 * Method that fills the library combobox
	 */
	private void setLibraryCB() {
		selectLibraryCB.getItems().clear();
		// Fill libraryCB with data
		for (BibliotheekModel library : db.getAllLibraries()) {
			selectLibraryCB.getItems().add(library.getName());
			updateLibraryCB.getItems().add(library.getName());
		}
	}

	/**
	 * Method that fills the genre combobox
	 */
	private void setGenreCB() {
		genreCB.getItems().clear();
		// Fill genreCombobox with data
		for (GenreModel genre : db.getAllGenres()) {
			genreCB.getItems().add(genre.getGenreName());
		}
	}

	/**
	 * Method that sets the ISBN combobox based on a chosen (or not chosen) library
	 * 
	 * @param name
	 */
	private void setBookCB(String name) {
		selectISBNCB.getItems().clear();
		// Check if the the librarytitel is given in the selectlibraryCombobox
		if (name.equals("")) {
			// Fill selectISBNCombobox with all Book ISBN's
			for (BoekModel book : db.getAllBooks()) {
				selectISBNCB.getItems().add(book.getISBN());
			}
		} else {
			// Fill selectISBNCombobox with Book ISBN's from chosen library
			for (BoekModel book : db.getAllBooksFromLibary(name)) {
				selectISBNCB.getItems().add(book.getISBN());
			}
		}
	}

	/**
	 * Method that updates the database with given data
	 * 
	 * @return int
	 */
	private int updateBook() {
		// Initiate objects an entities that are used in this method
		BoekModel book = new BoekModel();
		ArrayList<String> authorNames = new ArrayList<String>();

		// Check if updateLibraryCombobox and bookCaseCombobox are filled
		if (!updateLibraryCB.getSelectionModel().isEmpty() && !bookCaseCB.getSelectionModel().isEmpty()) {
			// Clear updatelibraryCB and bookcaseCB
			updateLibraryCB.getSelectionModel().clearSelection();
			bookCaseCB.getSelectionModel().clearSelection();
			// Fill bookmodel with data
			book.setGenre(genreCB.getValue().toString());
			book.setTitle(titleTextField.getText());
			book.setLanguage(languageTextField.getText());
			book.setReleaseDate(releaseDateTextField.getText());
			book.setIntTitle(intTitleTextField.getText());
			book.setDescription(ta.getText());
			book.setISBN(selectISBNCB.getValue().toString());
			book.setImage(B64STRING);
			B64STRING = null;

			// Gets all chosen authors from the authorcomboboxes and places the names in an
			// arraylist
			for (int i = 0; i < authorCount; i++) {
				if (!authors[i].getSelectionModel().isEmpty()
						&& !authorNames.contains(authors[i].getSelectionModel().getSelectedItem())) {
					authorNames.add(authors[i].getValue().toString());
				} else {
					authors[i].setValue(null);
				}
			}

			try {
				// Update database from given data
				db.insertBookHasAuthor(authorNames, book.getISBN());
				db.updateBookFromISBN(book);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			// Check if a book already exists in a library
			if (db.doesBookExistInLibrary(book.getISBN(), updateLibraryCB.getValue()) == 0) {
				// Update the book in bookcase
				db.updateBookCaseHasBook(Integer.parseInt(bookCaseCB.getValue()), selectLibraryCB.getValue().toString(),
						book.getISBN());
				System.out.println("hij hij update m");
			} else {
				// Insert book in a bookcase (and library)
				db.insertBookcaseHasBook(Integer.parseInt(bookCaseCB.getValue()), updateLibraryCB.getValue().toString(),
						book.getISBN(), "0");
				System.out.println("Hij voegt m toe");
			}

		} else {
			// Fill bookmodel with data from database
			book.setGenre(genreCB.getValue().toString());
			book.setTitle(titleTextField.getText());
			book.setLanguage(languageTextField.getText());
			book.setReleaseDate(releaseDateTextField.getText());
			book.setIntTitle(intTitleTextField.getText());
			book.setDescription(ta.getText());
			book.setImage(B64STRING);
			book.setISBN(selectISBNCB.getValue().toString());
			B64STRING = null;

			// Get all authors from the authorcomboboxes and set in an arraylist
			ArrayList<String> authorNames2 = new ArrayList<String>();
			for (int i = 0; i < authorCount; i++) {
				if (!authors[i].getSelectionModel().isEmpty()
						&& !authorNames2.contains(authors[i].getSelectionModel().getSelectedItem())) {
					authorNames2.add(authors[i].getValue().toString());
				} else {
					authors[i].setValue(null);
				}
			}
			
			try {
				// Update database to couple author(s) with a book
				db.insertBookHasAuthor(authorNames2, book.getISBN());
				return (db.updateBookFromISBN(book));
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		
		return 0;
	}
}