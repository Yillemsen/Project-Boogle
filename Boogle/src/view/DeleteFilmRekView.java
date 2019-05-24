package view;

import java.sql.ResultSet;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.Database;

public class DeleteFilmRekView extends GridPane {
	// Declaring variables
	private final Label libraryLabel, rackNrLabel, errorLabel;
	private final ComboBox libraryCB, rackNrCB;
	private final Button deleteButton;
	private final Text text;
        private ResultSet libraryResult, rackNrResult;
        private Database db = new Database();

	public DeleteFilmRekView(Pane mainPane) {
		// Instantiating Labels
		libraryLabel = new Label("Bibliotheek:");
		errorLabel = new Label("Filmrek <rackNr> is verwijderd");
		rackNrLabel = new Label("FilmrekNr:");

		// Instantiating Comboboxes
		libraryCB = new ComboBox();
                String strSQL = "select distinct bibliotheeknaam from filmrek";
            libraryResult = db.getData(strSQL);
            //database opzoeken
            try {
            while (libraryResult.next()) {
                String strItem = libraryResult.getString("bibliotheeknaam");
                libraryCB.getItems().add(strItem);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
            
            
		rackNrCB = new ComboBox();
                String strSQL1 = "SELECT `RekNummer` FROM `filmrek` WHERE BibliotheekNaam = 'Sunny'";
            rackNrResult = db.getData(strSQL1);
            //database opzoeken
            try {
            while (rackNrResult.next()) {
                String strItem = rackNrResult.getString("reknummer");
                rackNrCB.getItems().add(strItem);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

		// Instantiating Buttons
		deleteButton = new Button("Verwijder");

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

		// Placing ComboBox
		this.add(libraryCB, 1, 1);
		this.add(rackNrCB, 1, 2);

		// Placing Buttons
		this.add(deleteButton, 0, 3);

		// Add this gridpane to mainpane
		mainPane.getChildren().add(this);
	}
}
