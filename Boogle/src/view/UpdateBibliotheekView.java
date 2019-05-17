package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.Database;

public class UpdateBibliotheekView extends GridPane {

	// Declaring objects
	private final Label nameLabel, adresLabel, locationLabel, cellLabel, errorLabel;
	private final TextField nameTextField, adresTextField, locationTextField, cellTextField;
	private final Button saveButton;
	private final Text text;
	private Database db;

	public UpdateBibliotheekView(Pane mainPane) {
		//Instantiating Database
		db = new Database();
		
		// Instantiating labelobjects
		nameLabel = new Label("Naam:");
		adresLabel = new Label("Adres:");
		locationLabel = new Label("Plaats:");
		cellLabel = new Label("Telefoon:");
		errorLabel = new Label("Error, niet alle velden zijn (correct) ingevuld");

		// Instantiating textfieldobjects
		nameTextField = new TextField();
		adresTextField = new TextField();
		locationTextField = new TextField();
		cellTextField = new TextField();

		// Instantiating buttonobjects
		saveButton = new Button("Opslaan");

		// Instantiating textobjects
		text = new Text("Bibliotheek aanpassen");

		// Make-up for text and layout
		text.setStyle("-fx-font: 17 arial");
		setPadding(new Insets(5, 5, 5, 5));
		setHgap(5);
		setVgap(5);

		// Place textobject
		this.add(text, 0, 0);

		// Place labelobjects with for loop
		Label[] labelObjects = { nameLabel, adresLabel, locationLabel, cellLabel };
		for (int i = 0; i < labelObjects.length; i++) {
			this.add(labelObjects[i], 0, i + 1);
		}

		// Place textfieldobjects with for loop
		TextField[] textFieldObjects = { nameTextField, adresTextField, locationTextField, cellTextField };
		for (int i = 0; i < textFieldObjects.length; i++) {
			this.add(textFieldObjects[i], 1, i + 1);
		}

		// Place buttonobject
		this.add(saveButton, 1, 5);

		// Place errorlabelobject
		this.add(errorLabel, 1, 6);

		mainPane.getChildren().add(this);
		
	}
}