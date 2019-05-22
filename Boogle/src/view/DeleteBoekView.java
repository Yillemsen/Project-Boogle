package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class DeleteBoekView extends GridPane{
	// Declaring variables
	private final Label iSBNLabel, errorLabel;
	private final ComboBox nameCB;
	private final Button deleteButton;
	private final Text text;

	public DeleteBoekView(Pane mainPane) {
		// Instantiating Labels
		iSBNLabel = new Label("ISBN:");
		errorLabel = new Label("Boek <titel> is verwijderd");

		// Instantiating Comboboxes
		nameCB = new ComboBox();

		// Instantiating Buttons
		deleteButton = new Button("Verwijder");

		// Instantiating Text
		text = new Text("Boek");

		// Make-up for text and layout
		text.setStyle("-fx-font: 17 arial");
		this.setPadding(new Insets(5, 5, 5, 5));
		this.setHgap(5);
		this.setVgap(5);

		// Placing Textobjects
		this.add(text, 0, 0);

		// Place labelobjects
		this.add(iSBNLabel, 0, 1);
		this.add(errorLabel, 0, 3);

		// Placing ComboBox
		this.add(nameCB, 1, 1);

		// Placing Buttons
		this.add(deleteButton, 0, 2);

		// Add this gridpane to mainpane
		mainPane.getChildren().add(this);
	}

}
