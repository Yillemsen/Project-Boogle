package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.BibliotheekModel;
import model.Database;

public class UpdateBibliotheekView extends GridPane {

	// Declaring objects
	private final Label selectLibraryLabel, nameLabel, adresLabel, locationLabel, cellLabel, errorLabel;
	private final TextField nameTextField, adresTextField, locationTextField, cellTextField;
	private final Button saveButton, getItemsButton;
	private final Text text;
	private final ComboBox<String> selectLibraryCB;
	private final Database db;

	public UpdateBibliotheekView(Pane mainPane) {
		// Instantiating objects
		db = new Database();

		// Instantiating labelobjects
		selectLibraryLabel = new Label("Selecteer bibliotheek:");
		nameLabel = new Label("Naam:");
		adresLabel = new Label("Adres:");
		locationLabel = new Label("Plaats:");
		cellLabel = new Label("Telefoon:");
		errorLabel = new Label("");

		// Instantiating textfieldobjects
		nameTextField = new TextField();
		adresTextField = new TextField();
		locationTextField = new TextField();
		cellTextField = new TextField();

		// Instantiating buttonobjects
		saveButton = new Button("Opslaan");
		getItemsButton = new Button("Haal op");

		// Instantiating textobjects
		text = new Text("Bibliotheek aanpassen");

		// Instantiating Comboboxobjects
		selectLibraryCB = new ComboBox<String>();
		setLibraryCB();

		// Make-up for text, labels and layout
		text.setStyle("-fx-font: 17 arial");
		setPadding(new Insets(5, 5, 5, 5));
		setHgap(5);
		setVgap(5);

		// Place textobject
		this.add(text, 0, 0);

		// Place Comboboxes
		this.add(selectLibraryCB, 1, 1);

		// Place labelobjects
		this.add(selectLibraryLabel, 0, 1);

		// Place buttonobject
		this.add(getItemsButton, 1, 2);
		this.add(saveButton, 1, 7);

		// Place errorlabelobject
		this.add(errorLabel, 1, 8);

		// Place labelobjects with for loop
		Label[] labelObjects = { nameLabel, adresLabel, locationLabel, cellLabel };
		for (int i = 0; i < labelObjects.length; i++) {
			this.add(labelObjects[i], 0, i + 3);
		}

		// Place textfieldobjects with for loop
		TextField[] textFieldObjects = { nameTextField, adresTextField, locationTextField, cellTextField };
		for (int i = 0; i < textFieldObjects.length; i++) {
			this.add(textFieldObjects[i], 1, i + 3);
		}

		// Set onactionlistener for getItemsButton
		getItemsButton.setOnAction(event -> {
			setLibraryItems();
			errorLabel.setText("Data is opgehaald");
		});

		// Set onactionlistener for saveButton
		saveButton.setOnAction(event -> {
			if (updateLibraryItems() == 0) {
				errorLabel.setText("Error, wijzigingen zijn niet opgeslagen");
			} else {
				errorLabel.setText("Wijzigingen zijn opgeslagen");
			}
			setLibraryCB();
		});

		mainPane.getChildren().add(this);
	}

	/**
	 * Method that fills the library combobox
	 */
	private void setLibraryCB() {
		selectLibraryCB.getItems().clear();
		for (BibliotheekModel library : db.getAllLibraries()) {
			selectLibraryCB.getItems().add(library.getName());
		}
	}

	/**
	 * Method that sets the libraryitems
	 */
	private void setLibraryItems() {
		BibliotheekModel bm = new BibliotheekModel();
		try {
			bm = db.getLibraryFromName(selectLibraryCB.getValue().toString());
		} catch (NullPointerException e) {
			errorLabel.setText("Error, er is geen bibliotheek geselecteerd om aan te passen");
		}

		nameTextField.setText(bm.getName());
		adresTextField.setText(bm.getAdres());
		locationTextField.setText(bm.getLocation());
		cellTextField.setText(bm.getCell());
	}

	/**
	 * Method that updates the librarymodel in the database
	 * 
	 * @return int
	 */
	private int updateLibraryItems() {
		BibliotheekModel bm = new BibliotheekModel();
		bm.setName(nameTextField.getText());
		bm.setAdres(adresTextField.getText());
		bm.setLocation(locationTextField.getText());
		bm.setCell(cellTextField.getText());
		try {
			String oldName = selectLibraryCB.getValue().toString();
			return (db.updateLibrary(bm, oldName));
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return 0;
	}
}