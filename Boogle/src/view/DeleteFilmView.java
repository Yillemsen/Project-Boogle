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

public class DeleteFilmView extends GridPane{
	// Declaring variables
	private final Label titleLabel, errorLabel;
	private final ComboBox nameCB;
	private final Button deleteButton;
	private final Text text;
        private ResultSet nameResult;
        private Database db = new Database();

	public DeleteFilmView(Pane mainPane) {
		// Instantiating Labels
		titleLabel = new Label("Titel:");
		errorLabel = new Label("Film <title> is verwijderd");

		// Instantiating Comboboxes
		nameCB = new ComboBox();
                String strSQL = "select * from film";
            nameResult = db.getData(strSQL);
            //database opzoeken
            try {
            while (nameResult.next()) {
                String strItem = nameResult.getString("titel");
                nameCB.getItems().add(strItem);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

		// Instantiating Buttons
		deleteButton = new Button("Verwijder");

		// Instantiating Text
		text = new Text("Film");

		// Make-up for text and layout
		text.setStyle("-fx-font: 17 arial");
		this.setPadding(new Insets(5, 5, 5, 5));
		this.setHgap(5);
		this.setVgap(5);

		// Placing Textobjects
		this.add(text, 0, 0);

		// Place labelobjects
		this.add(titleLabel, 0, 1);
		this.add(errorLabel, 0, 3);

		// Placing ComboBox
		this.add(nameCB, 1, 1);

		// Placing Buttons
		this.add(deleteButton, 0, 2);

		// Add this gridpane to mainpane
		mainPane.getChildren().add(this);
	}

}
