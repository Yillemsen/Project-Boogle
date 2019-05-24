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
import model.AuteurModel;
import model.Database;

public class UpdateAuteurView extends GridPane {
	private final Label selectAuthorLabel, nameLabel, dobLabel, dodLabel, errorLabel;
	private final TextField nameTextField, dobTextField, dodTextField;
	private final Button saveButton, getItemsButton;
	private final Text text;
	private final ComboBox<String> selectAuthorCB;
	private final Database db;

	public UpdateAuteurView(Pane mainPane) {
		// Instantiating objects
		db = new Database();

		// Instantiating labelobjects
		selectAuthorLabel = new Label("Selecteer auteur:");
		nameLabel = new Label("Naam:");
		dobLabel = new Label("Geboortedatum");
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
		text = new Text("Auteur aanpassen");

		// Instantiating combobox
		selectAuthorCB = new ComboBox();

		// Make-up for text and layout
		text.setStyle("-fx-font: 17 arial");
		setPadding(new Insets(5, 5, 5, 5));
		setHgap(5);
		setVgap(5);

		// Place textobject
		this.add(text, 0, 0);

		// Place Comboboxes
		this.add(selectAuthorCB, 1, 1);

		// Place buttonobject
		this.add(getItemsButton, 1, 2);
		this.add(saveButton, 1, 6);

		// Place labelobject
		this.add(errorLabel, 1, 7);
		this.add(selectAuthorLabel, 0, 1);

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

		saveButton.setOnAction(event -> {
			if (updateAuthorItems() == 0) {
				errorLabel.setText("Error, wijzigingen zijn niet opgeslagen");
			} else {
				errorLabel.setText("Wijzigingen zijn opgeslagen");
			}
			setAuthorCB();
		});

		getItemsButton.setOnAction(event -> {
			setAuthorItems();
			errorLabel.setText("Data is opgehaald");
		});

		setAuthorCB();

		mainPane.getChildren().add(this);
	}

	private int updateAuthorItems() {
		AuteurModel am = new AuteurModel();
		am.setName(nameTextField.getText());
		am.setBirth(dobTextField.getText());
		am.setDeath(dodTextField.getText());

		String oldName = selectAuthorCB.getValue().toString();
		return (db.updateAuthor(am, oldName));
	}

	private void setAuthorItems() {
		AuteurModel am = new AuteurModel();
		am = db.getAuthorFromName((selectAuthorCB.getValue().toString()));

		nameTextField.setText(am.getName());
		dobTextField.setText(am.getBirth());
		dodTextField.setText(am.getDeath());
	}

	private void setAuthorCB() {
		selectAuthorCB.getItems().clear();
		for (AuteurModel author : db.getAllAuthors()) {
			selectAuthorCB.getItems().add(author.getName());
		}
	}
}
