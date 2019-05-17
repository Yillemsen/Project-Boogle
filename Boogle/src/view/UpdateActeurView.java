package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class UpdateActeurView extends GridPane {
	private final Label selectNameLabel, nameLabel, dobLabel, dodLabel, errorLabel;
	private final TextField nameTextField, dobTextField, dodTextField;
	private final Button saveButton;
	private final Text text;
	private final ComboBox selectNameCB;

	public UpdateActeurView(Pane mainPane) {
		// Instantiating labelobjects
		selectNameLabel = new Label("Selecteer acteur:");
		nameLabel = new Label("Naam:");
		dobLabel = new Label("Geboortedatum:");
		dodLabel = new Label("overlijdingsdatum:");
		errorLabel = new Label("Error, niet alle velden zijn (correct) ingevuld");

		// Instantiating textfieldobjects
		nameTextField = new TextField();
		dobTextField = new TextField();
		dodTextField = new TextField();

		// Instantiating buttonobjects
		saveButton = new Button("Opslaan");

		// Instantiating textobjects
		text = new Text("Acteur aanpassen");

		// Instantiating Comboboxes
		selectNameCB = new ComboBox();

		// Make-up for text and layout
		text.setStyle("-fx-font: 17 arial");
		setPadding(new Insets(5, 5, 5, 5));
		setHgap(5);
		setVgap(5);

		// Place textobject
		this.add(text, 0, 0);
		
		// Place comboboxes
		this.add(selectNameCB, 1, 1);

		// Place buttonobject
		this.add(saveButton, 1, 4);

		// Place errorlabelobject
		this.add(errorLabel, 1, 5);

		// Place labelobjects with for loop
		Label[] labelObjects = { selectNameLabel, nameLabel, dobLabel, dodLabel };
		for (int i = 0; i < labelObjects.length; i++) {
			this.add(labelObjects[i], 0, i + 1);
		}

		// Place textfieldobjects with for loop
		TextField[] textFieldObjects = { nameTextField, dobTextField, dodTextField };
		for (int i = 0; i < textFieldObjects.length; i++) {
			this.add(textFieldObjects[i], 1, i + 2);
		}

		mainPane.getChildren().add(this);
	}
}