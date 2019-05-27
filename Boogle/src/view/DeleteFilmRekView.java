package view;

import java.sql.ResultSet;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.ActeurModel;
import model.BoekenkastModel;
import model.Database;
import model.FilmrekModel;

public class DeleteFilmRekView extends GridPane {
	// Declaring variables
	private final Label libraryLabel, rackNrLabel, errorLabel, errorLabel1;
	private final ComboBox libraryCB, rackNrCB;
	private final Button deleteButton, valueButton;
	private final Text text;
        private Database db = new Database();

	public DeleteFilmRekView(Pane mainPane) {
		// Instantiating Labels
		libraryLabel = new Label("Bibliotheek:");
		errorLabel = new Label("Filmrek <rackNr> is verwijderd");
		rackNrLabel = new Label("FilmrekNr:");
                errorLabel1 = new Label("");

		// Instantiating Comboboxes
		libraryCB = new ComboBox();
                setFilmrekCB();
                        
		rackNrCB = new ComboBox();
               // setFilmrekkCB();
                

		// Instantiating Buttons
		deleteButton = new Button("Verwijder");
                
                valueButton = new Button("Haal op");
                valueButton.setOnAction(event -> {
                        setFilmrekkCB();
			setLibraryItems();
			errorLabel1.setText("Data is opgehaald");
		});

		// Instantiating Text
		text = new Text("Filmrek");

		// Make-up for text and layout
		text.setStyle("-fx-font: 17 arial");
		this.setPadding(new Insets(5, 5, 5, 5));
		this.setHgap(5);
		this.setVgap(5);

		// Placing Textobjects
		this.add(text, 0, 0);

		// Place labelobjects
		this.add(libraryLabel, 0, 1);
		this.add(rackNrLabel, 0, 2);
		this.add(errorLabel, 0, 4);
                this.add(errorLabel1, 0, 4);

		// Placing ComboBox
		this.add(libraryCB, 1, 1);
		this.add(rackNrCB, 1, 2);

		// Placing Buttons
		this.add(deleteButton, 0, 3);
                this.add(valueButton, 2,2);

		// Add this gridpane to mainpane
		mainPane.getChildren().add(this);
	}
        private void setFilmrekCB() {
		libraryCB.getItems().clear();
		for (FilmrekModel filmrek : db.getAllFilmrekken()) {
			libraryCB.getItems().add(filmrek.getLibraryName());
		}
	}
        private void setFilmrekkCB() {
		rackNrCB.getItems().clear();
		for (FilmrekModel filmrekk : db.getAllFilmrekkenn(libraryCB.getValue().toString())) {
			rackNrCB.getItems().add(filmrekk.getRackNr());
		}
	}
        private void setLibraryItems() {
		FilmrekModel bm = new FilmrekModel();
		bm = db.getFilmrekFromName(libraryCB.getValue().toString());
	}
}
