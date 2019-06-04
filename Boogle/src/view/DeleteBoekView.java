package view;

import java.sql.ResultSet;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.BoekModel;
import model.Database;

public class DeleteBoekView extends GridPane{
	// Declaring variables
	private final Label iSBNLabel, errorLabel;
	private final ComboBox isbnCB;
	private final Button deleteButton;
	private final Text text;
        private ArrayList<String> ISBNItems;
        private Database db = new Database();

	public DeleteBoekView(Pane mainPane) {
		// Instantiating Labels
		iSBNLabel = new Label("ISBN:");
		errorLabel = new Label("");
                
                // Instantiating Arraylists
		ISBNItems = new ArrayList<String>();

		// Instantiating Comboboxes
		isbnCB = new ComboBox();
                setComboBoxArrayLists();
		//isbnCB.getItems().add(ISBNItems.get(0));
                

		// Instantiating Buttons
		deleteButton = new Button("Verwijder");
                deleteButton.setOnAction(event -> {
                        //give error if it fails
			if (deleteBookItems() == 0) {
				errorLabel.setText("Het verwijderen is mislukt");
			} else {
				errorLabel.setText("Het is verwijderd van de database");
			}
                });

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
		this.add(isbnCB, 1, 1);

		// Placing Buttons
		this.add(deleteButton, 0, 2);

		// Add this gridpane to mainpane
		mainPane.getChildren().add(this);
	}
        //get ISBN from database
        private void setComboBoxArrayLists() {

		for (BoekModel boek : db.getAllBooks()) {
			System.out.println(boek.getISBN());
			ISBNItems.add(boek.getISBN());
                        isbnCB.getItems().add(boek.getISBN());

		}
	}
        //delete from database
        private int deleteBookItems() {
            String isbn = isbnCB.getValue().toString();
                       return(db.deleteBoek(isbn));
        }
}
