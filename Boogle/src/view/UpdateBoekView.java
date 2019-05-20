package view;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.BoekModel;
import model.Database;

public class UpdateBoekView extends GridPane {
	private final Label selectISBNLabel, libraryLabel, bookCaseNrLabel, genreLabel, ISBNLabel, bookNrLabel, titleLabel, languageLabel, releaseDateLabel, intTitle, authorLabel, errorLabel;
	private final TextField ISBNTextField, bookNrTextField, titleTextField, languageTextField, releaseDateTextField, intTitleTextField;
	private final Button saveButton, addBookCaseButton, addAuthorTextFieldButton;
	private final Text text;
	private final ComboBox ISBNCB, libraryCB, bookCaseCB, genreCB, authorCB;
	private final Database db;
	private ArrayList<String> ISBNItems, libraryItems, bookCaseItems, genreItems, authorItems;

	public UpdateBoekView(Pane mainPane) {
		//Instantiating objects
		db = new Database();
		
		// Instantiating labelobjects
		selectISBNLabel = new Label("Selecteer ISBN:");
		libraryLabel = new Label("Bibliotheek:");
		bookCaseNrLabel = new Label("Boekenkast:");
		ISBNLabel = new Label("ISBN:");
		bookNrLabel = new Label("BoekNr:");
		titleLabel = new Label("Titel:");
		languageLabel = new Label("Taal:");
		releaseDateLabel = new Label("Datum uitgave:");
		intTitle = new Label("Internationale titel:");
		genreLabel = new Label("Genre:");
		authorLabel = new Label("Auteur");
		errorLabel = new Label("Error, niet alle velden zijn (correct) ingevuld");

		// Instantiating textfieldobjects
		ISBNTextField = new TextField();
		bookNrTextField = new TextField();
		titleTextField = new TextField();
		languageTextField = new TextField();
		releaseDateTextField = new TextField();
		intTitleTextField = new TextField();
		
		// Instantiating buttonobjects
		saveButton = new Button("Opslaan");
		addBookCaseButton = new Button("+");
		addAuthorTextFieldButton = new Button("+");

		// Instantiating textobjects
		text = new Text("Boek aanpassen");
		
		//Instantiating comboboxes
		ISBNCB = new ComboBox(setComboBoxArrayLists());
		libraryCB = new ComboBox();
		bookCaseCB = new ComboBox();
		genreCB = new ComboBox();
		authorCB = new ComboBox();
		
		setComboBoxArrayLists();
		
		

		// Make-up for text and layout
		text.setStyle("-fx-font: 17 arial");
		setPadding(new Insets(5, 5, 5, 5));
		setHgap(5);
		setVgap(5);

		// Place textobject
		this.add(text, 0, 0);

		// Place labelobjects with for loop
		Label[] labelObjects = { selectISBNLabel, libraryLabel, bookCaseNrLabel, genreLabel, ISBNLabel, bookNrLabel, titleLabel, languageLabel, releaseDateLabel, intTitle, authorLabel };
		for (int i = 0; i < labelObjects.length; i++) {
			this.add(labelObjects[i], 0, i + 1);
		}

		// Place textfieldobjects with for loop
		TextField[] textFieldObjects = { ISBNTextField, bookNrTextField, titleTextField, languageTextField, releaseDateTextField, intTitleTextField };
		for (int i = 0; i < textFieldObjects.length; i++) {
			this.add(textFieldObjects[i], 1, i + 5);
		}
		
		//Place comboboxes
		this.add(ISBNCB, 1, 1);
		this.add(libraryCB, 1, 2);
		this.add(bookCaseCB, 1, 3);
		this.add(genreCB, 1, 4);
		this.add(authorCB, 1, 11);

		// Place buttonobject
		this.add(saveButton, 1, 13);
		this.add(addBookCaseButton, 2, 3);
		this.add(addAuthorTextFieldButton, 2, 11);

		// Place errorlabelobject
		this.add(errorLabel, 1, 14);

		mainPane.getChildren().add(this);
	}
	
	private ObservableList<String> setComboBoxArrayLists() {
		for(BoekModel boek: db.getAllBooks()) {
			System.out.println(boek.getISBN());
			ISBNItems.add(boek.getISBN());
		}
		ObservableList<String> isbn = FXCollections.observableArrayList(ISBNItems);
		return isbn;
	}
}