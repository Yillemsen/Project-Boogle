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

public class DeleteBibliotheekView extends GridPane {
	// Declaring variables
	private final Label nameLabel, errorLabel;
	private final ComboBox nameCB;
	private final Button deleteButton;
	private final Text text;
        private ResultSet nameResult;
        private Database db = new Database();

	public DeleteBibliotheekView(Pane mainPane) {
		// Instantiating Labels
		nameLabel = new Label("Naam:");
		errorLabel = new Label("Bibliotheek <library> is verwijderd");

		// Instantiating Comboboxes
		nameCB = new ComboBox();
                String strSQL = "select * from bibliotheek";
            nameResult = db.getData(strSQL);
            //database opzoeken
            try {
            while (nameResult.next()) {
                String strItem = nameResult.getString("naam");
                nameCB.getItems().add(strItem);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

		// Instantiating Buttons
		deleteButton = new Button("Verwijder");

		// Instantiating Text
		text = new Text("Bibliotheek");

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

}
