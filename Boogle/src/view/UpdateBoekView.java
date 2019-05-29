package view;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
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
			getBookDataFromDb, clearLibraryButton;
	private final Text text, selectText, updateText;
	private final ComboBox<String> selectISBNCB, selectLibraryCB, bookCaseCB, genreCB, updateLibraryCB;
	private final Database db;
	private final TextArea ta;
	private int authorCount = 0;
	private final ComboBox<String>[] authors = new ComboBox[5];

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
		this.add(saveButton, 1, 18);
		this.add(addAuthorTextFieldButton, 2, 13);
		this.add(getISBNFromLibraryButton, 2, 3);
		this.add(getBookCaseFromLibraryButton, 2, 5);
		this.add(getBookDataFromDb, 2, 2);
		this.add(clearLibraryButton, 3, 3);

		// Place textobject
		this.add(text, 0, 0);
		this.add(selectText, 0, 1);
		this.add(updateText, 0, 4);

		// Place labelobject
		this.add(errorLabel, 1, 19);
		this.add(selectISBNLabel, 0, 2);
		this.add(libraryLabel, 0, 3);

		// Place textareas
		this.add(ta, 1, 12);

		// Fill comboboxes with initial data
		setLibraryCB();
		setGenreCB();
		setBookCB("");

		// Set onactionlisteners for clearlibrarybutton
		clearLibraryButton.setOnAction(event -> {
			selectLibraryCB.getSelectionModel().clearSelection();
			setBookCB("");
		});

		// Set onactionlisteners for getISBNFromLibraryButton
		getISBNFromLibraryButton.setOnAction(event -> {
			if (!selectLibraryCB.getSelectionModel().isEmpty()) {
				String libraryName = selectLibraryCB.getValue().toString();
				setBookCB(libraryName);
				errorLabel.setText("Boeken zijn opgehaald uit bibliotheek " + libraryName);
			} else {
				errorLabel.setText("Error, er is geen bibliotheek gekozen");
			}
		});

		// Set onactionlistener for getBookCaseFromLibraryButton
		getBookCaseFromLibraryButton.setOnAction(event -> {
			if (!updateLibraryCB.getSelectionModel().isEmpty()) {
				String libraryName = updateLibraryCB.getValue().toString();
				setBookCaseCB(libraryName);
				errorLabel.setText("Boekenkasten zijn opgehaald uit bibliotheek " + libraryName);
			}
		});

		// Set onactionlistener for saveButton
		saveButton.setOnAction(event -> {
			if (!(updateBook() == 0)) {
				errorLabel.setText("Error, niet alle velden zijn correct ingevuld");
			} else {
				errorLabel.setText("Data is opgeslagen");
			}
		});

		// Set onactionlistener for getBookDataFromDb
		getBookDataFromDb.setOnAction(event -> {
			if (!selectISBNCB.getSelectionModel().isEmpty()) {
				setAuthorItems(selectISBNCB.getValue().toString());
				setBookItems();
				errorLabel.setText("Data is opgehaald");
			} else {
				errorLabel.setText("Error, er is geen ISBN geselecteerd");
			}
		});

		// set onactionlistener for addAuthorTextFieldButton
		addAuthorTextFieldButton.setOnAction(event -> {
			if (authorCount < 5) {
				authors[authorCount] = new ComboBox<String>();
				this.add(authors[authorCount], 1, authorCount + 13);

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
		if (db.getAllAuthorsFromBook(iSBN) != null) {
			// Create authorcomboboxes if needed and fill with data
			while (i < db.getAllAuthorsFromBook(iSBN).size()) {
				if (i >= authorCount) {
					authors[authorCount] = new ComboBox<String>();
					this.add(authors[authorCount], 1, authorCount + 13);
					for (AuteurModel author : db.getAllAuthors()) {
						authors[authorCount].getItems().add(author.getName());
					}
					authorCount++;
				}
				i++;
			}
			// Fill the value of selectedauthors
			int p = 0;
			while (p < i) {
				for (AuteurModel author : db.getAllAuthorsFromBook(iSBN)) {
					authors[p].setValue(author.getName());
					p++;
				}
			}
		}
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
			book = db.getBookFromISBN(selectISBNCB.getValue().toString());
		} catch (NullPointerException e) {
			errorLabel.setText("Error, er is geen ISBN geselecteerd om aan te passen");
		}

		try {
			bookCase = db.getBookcaseFromISBN(selectISBNCB.getValue().toString(),
					selectLibraryCB.getValue().toString());
		} catch (NullPointerException e) {
		}

		genreCB.setValue(book.getGenre());
		titleTextField.setText(book.getTitle());
		languageTextField.setText(book.getLanguage());
		releaseDateTextField.setText(book.getReleaseDate());
		intTitleTextField.setText(book.getIntTitle());
		ta.setText(book.getDescription());

		if (!selectLibraryCB.getSelectionModel().isEmpty()) {
			String libraryName = selectLibraryCB.getValue().toString();
			updateLibraryCB.setValue(libraryName);
			String bookCaseString = bookCase.getBookCaseNr() + "";
			bookCaseCB.setValue(bookCaseString);
		}
	}

	/**
	 * Method that fills the bookcase Combobox based on chosen library
	 * 
	 * @param name
	 */
	private void setBookCaseCB(String name) {
		bookCaseCB.getItems().clear();
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
		if (name.equals("")) {
			for (BoekModel book : db.getAllBooks()) {
				selectISBNCB.getItems().add(book.getISBN());
			}
		} else {
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
		BoekModel book = new BoekModel();
		String authorName = "";
		BoekenkastModel bookcase = new BoekenkastModel();
		BibliotheekModel library = new BibliotheekModel();
		int h = 0;
		ArrayList<String> authorNames = new ArrayList<String>();

		if (!updateLibraryCB.getSelectionModel().isEmpty()) {
			if (!bookCaseCB.equals("")) {
				// Here comes the code for updating the whole book with library
				book.setGenre(genreCB.getValue().toString());
				book.setTitle(titleTextField.getText());
				book.setLanguage(languageTextField.getText());
				book.setReleaseDate(releaseDateTextField.getText());
				book.setIntTitle(intTitleTextField.getText());
				book.setDescription(ta.getText());
				// book.setImage(image);

				for (int i = 0; i < authorCount; i++) {
					if (!authors[i].getSelectionModel().isEmpty()) {
						authorNames.add(authors[i].getValue().toString());
					}
				}
				try {
					book.setISBN(selectISBNCB.getValue().toString());
					System.out.println(authorNames);
					db.insertBookHasAuthor(authorNames, book.getISBN());
					return (db.updateBookFromISBN(book));
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
				while (h < 5) {
					if (authors[h] != null) {
						authorName = (authors[h].getValue().toString());
						authorNames.add(authorName);
					}
					h++;
				}
				db.insertBookHasAuthor(authorNames, book.getISBN());
				if (db.doesBookExistInLibrary(book.getISBN(), updateLibraryCB.getValue().toString()) == 1) {
					db.updateBookCaseHasBook(Integer.parseInt(bookCaseCB.getValue()),
							updateLibraryCB.getValue().toString(), book.getISBN(),
							selectLibraryCB.getValue().toString());
				} else {
					db.insertBookcaseHasBook(Integer.parseInt(bookCaseCB.getValue()),
							updateLibraryCB.getValue().toString(), book.getISBN());
				}

			} else {
				errorLabel.setText("Error, er is geen boekenkast geselecteerd");
				return 0;
			}
		} else {

			book.setGenre(genreCB.getValue().toString());
			book.setTitle(titleTextField.getText());
			book.setLanguage(languageTextField.getText());
			book.setReleaseDate(releaseDateTextField.getText());
			book.setIntTitle(intTitleTextField.getText());
			book.setDescription(ta.getText());
			// book.setImage(image);
			ArrayList<String> authorNames2 = new ArrayList<String>();
			for (int i = 0; i < authorCount; i++) {
				if (!authors[i].getSelectionModel().isEmpty()) {
					authorNames2.add(authors[i].getValue().toString());
				}
			}
			try {
				book.setISBN(selectISBNCB.getValue().toString());
				System.out.println(authorNames2);
				db.insertBookHasAuthor(authorNames2, book.getISBN());
				return (db.updateBookFromISBN(book));
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
}