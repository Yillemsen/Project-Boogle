package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.AuteurModel;
import model.Database;

public class DeleteAuteurView extends GridPane{
	// Declaring variables
	private final Label nameLabel, errorLabel;
	private final ComboBox nameCB;
	private final Button deleteButton;
	private final Text text;
        private Database db = new Database();

	public DeleteAuteurView(Pane mainPane) {
		// Instantiating Labels
		nameLabel = new Label("Naam:");
		errorLabel = new Label("");

		// Instantiating Comboboxes
		nameCB = new ComboBox();
                setAuthorCB();

		// Instantiating Buttons
		deleteButton = new Button("Verwijder");
                deleteButton.setOnAction(event -> {
                    //give error if it fails
			if (deleteAuteurItems() == 0) {
				errorLabel.setText("Het verwijderen is mislukt");
			} else {
				errorLabel.setText("Het is verwijderd van de database");
			}
			
		});

		// Instantiating Text
		text = new Text("Auteur");

		// Make-up for text and layout
		text.setStyle("-fx-font: 17 arial");
		this.setPadding(new Insets(5, 5, 5, 5));
		this.setHgap(5);
		this.setVgap(5);

		// Placing Textobjects
		this.add(text, 0, 0);

		// Place labelobjects
		this.add(nameLabel, 0, 1);
		this.add(errorLabel, 0, 3);

		// Placing ComboBox
		this.add(nameCB, 1, 1);

		// Placing Buttons
		this.add(deleteButton, 0, 2);

		// Add this gridpane to mainpane
		mainPane.getChildren().add(this);
	}
        //get actorname from database
        private void setAuthorCB() {
		for (AuteurModel author : db.getAllAuthors()) {
			nameCB.getItems().add(author.getName());
		}
	}
        //delete from database
        private int deleteAuteurItems() {
            String name = nameCB.getValue().toString();
                       return(db.deleteAuteur(name));
        }

}
