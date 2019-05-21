package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.Database;

public class UpdateBibliotheekView extends GridPane {

	// Declaring objects
	private final Label selectLibraryLabel, nameLabel, adresLabel, locationLabel, cellLabel, errorLabel;
	private final TextField nameTextField, adresTextField, locationTextField, cellTextField;
	private final Button saveButton;
	private final Text text;
	private final ComboBox selectLibraryCB;
	

	public UpdateBibliotheekView(Pane mainPane) {
		// Instantiating labelobjects
		selectLibraryLabel = new Label("Selecteer bibliotheek:");
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
		
		//Instantiating Comboboxobjects
		selectLibraryCB = new ComboBox();

		// Make-up for text, labels and layout
		text.setStyle("-fx-font: 17 arial");
		setPadding(new Insets(5, 5, 5, 5));
		setHgap(5);
		setVgap(5);

		// Place textobject
		this.add(text, 0, 0);
		
		//Place Comboboxes
		this.add(selectLibraryCB, 1, 1);
		

		// Place buttonobject
		this.add(saveButton, 1, 6);

		// Place errorlabelobject
		this.add(errorLabel, 1, 7);

		// Place labelobjects with for loop
		Label[] labelObjects = { selectLibraryLabel, nameLabel, adresLabel, locationLabel, cellLabel };
		for (int i = 0; i < labelObjects.length; i++) {
			this.add(labelObjects[i], 0, i + 1);
		}

		// Place textfieldobjects with for loop
		TextField[] textFieldObjects = { nameTextField, adresTextField, locationTextField, cellTextField };
		for (int i = 0; i < textFieldObjects.length; i++) {
			this.add(textFieldObjects[i], 1, i + 2);
		}

		Database db = new Database();
		System.out.println(db.getAllLibraries().get(0).getName());
		System.out.println(db.getAllLibraries().get(1).getName());
		System.out.println(db.getAllLibraries().get(2).getName());
		System.out.println(db.getAllLibraries().get(3).getName());

		mainPane.getChildren().add(this);
		
	}
}