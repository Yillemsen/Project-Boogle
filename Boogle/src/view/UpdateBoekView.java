package view;

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
			selectLibraryCB.getItems().clear();
			setBookCB("");
		});

		// Set onactionlisteners for getISBNFromLibraryButton
		getISBNFromLibraryButton.setOnAction(event -> {
			String libraryName = selectLibraryCB.getValue().toString();
			setBookCB(libraryName);
			errorLabel.setText("Boeken zijn opgehaald uit bibliotheek " + libraryName);
		});

		// Set onactionlistener for getBookCaseFromLibraryButton
		getBookCaseFromLibraryButton.setOnAction(event -> {
			String libraryName = updateLibraryCB.getValue().toString();
			setBookCaseCB(libraryName);
			errorLabel.setText("Boekenkasten zijn opgehaald uit bibliotheek " + libraryName);
		});

		// Set onactionlistener for saveButton
		saveButton.setOnAction(event -> {

		});
		// Set onactionlistener for getBookDataFromDb
		getBookDataFromDb.setOnAction(event -> {
			errorLabel.setText("Data is opgehaald");
			setBookItems();
			setAuthorItems(selectISBNCB.getValue().toString());
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
		while (i < db.getAllAuthorsFromBook(iSBN).size()) {
			if (i + 1 > authorCount) {
				authors[authorCount] = new ComboBox<String>();
				this.add(authors[authorCount], 1, authorCount + 13);
			}
			for (AuteurModel author : db.getAllAuthorsFromBook(iSBN)) {
				authors[i].getItems().add(author.getName());

				authors[i].setValue(author.getName());
			}
			authorCount++;
			i++;
		}

		while (i + 1 < authorCount) {
			authors[i].valueProperty().set(null);
			i++;
		}
	}

	/**
	 * Method that sets the value of textfields from data of chosen book
	 */
	private void setBookItems() {
		BoekModel book = new BoekModel();
		BoekenkastModel bookCase = new BoekenkastModel();
		try {
			book = db.getBookFromISBN(selectISBNCB.getValue().toString());
			bookCase = db.getBookcaseFromISBN(selectISBNCB.getValue().toString(),
					selectLibraryCB.getValue().toString());
		} catch (NullPointerException e) {
			errorLabel.setText("Error, er is geen ISBN geselecteerd om aan te passen");
		}

		genreCB.setValue(book.getGenre());
		titleTextField.setText(book.getTitle());
		languageTextField.setText(book.getLanguage());
		releaseDateTextField.setText(book.getReleaseDate());
		intTitleTextField.setText(book.getIntTitle());
		ta.setText(book.getDescription());

		if (!selectLibraryCB.getValue().toString().equals("")) {
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
}