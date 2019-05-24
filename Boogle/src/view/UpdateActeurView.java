package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.ActeurModel;
import model.BibliotheekModel;
import model.Database;

public class UpdateActeurView extends GridPane {
	private final Label selectNameLabel, nameLabel, dobLabel, dodLabel, errorLabel;
	private final TextField nameTextField, dobTextField, dodTextField;
	private final Button saveButton, getItemsButton;
	private final Text text;
	private final ComboBox<String> selectNameCB;
	private final Database db;

	public UpdateActeurView(Pane mainPane) {
		// Instantiating objects
		db = new Database();

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
		getItemsButton = new Button("Haal op");

		// Instantiating textobjects
		text = new Text("Acteur aanpassen");

		// Instantiating Comboboxes
		selectNameCB = new ComboBox<String>();

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
		this.add(getItemsButton, 1, 2);
		this.add(saveButton, 1, 6);

		// Place labelobject
		this.add(selectNameLabel, 0, 1);
		this.add(errorLabel, 1, 7);

		// Place labelobjects with for loop
		Label[] labelObjects = { nameLabel, dobLabel, dodLabel };
		for (int i = 0; i < labelObjects.length; i++) {
			this.add(labelObjects[i], 0, i + 3);
		}

		// Place textfieldobjects with for loop
		TextField[] textFieldObjects = { nameTextField, dobTextField, dodTextField };
		for (int i = 0; i < textFieldObjects.length; i++) {
			this.add(textFieldObjects[i], 1, i + 3);
		}

		// Fill combobox
		setActorCB();

		getItemsButton.setOnAction(event -> {
			setActorItems();
			errorLabel.setText("Data is opgehaald");
		});

		saveButton.setOnAction(event -> {
			if (updateActorItems() == 0) {
				errorLabel.setText("Error, wijzigingen zijn niet opgeslagen");
			} else {
				errorLabel.setText("Wijzigingen zijn opgeslagen");
			}
			setActorCB();
		});

		mainPane.getChildren().add(this);
	}

	private int updateActorItems() {
		ActeurModel am = new ActeurModel();
		am.setName(nameTextField.getText());
		am.setBirth(dobTextField.getText());
		am.setDeath(dodTextField.getText());

		String oldName = selectNameCB.getValue().toString();
		return (db.updateActor(am, oldName));
	}

	private void setActorItems() {
		ActeurModel am = new ActeurModel();
		am = db.getActorFromName((selectNameCB.getValue().toString()));

		nameTextField.setText(am.getName());
		dobTextField.setText(am.getBirth());
		dodTextField.setText(am.getDeath());
	}

	private void setActorCB() {
		selectNameCB.getItems().clear();
		for (ActeurModel actor : db.getAllActors()) {
			selectNameCB.getItems().add(actor.getName());
		}
	}
}