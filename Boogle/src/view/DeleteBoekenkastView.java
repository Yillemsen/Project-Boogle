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

public class DeleteBoekenkastView extends GridPane {
	// Declaring variables
	private final Label libraryLabel, caseNrLabel, errorLabel;
	private final ComboBox libraryCB, caseNrCB;
	private final Button deleteButton;
	private final Text text;
        private ResultSet libraryResult;
        private Database db = new Database();

	public DeleteBoekenkastView(Pane mainPane) {
		// Instantiating Labels
		libraryLabel = new Label("Boekenkast:");
		errorLabel = new Label("Boekenkast <boekenkastNr> is verwijderd");
		caseNrLabel = new Label("BoekenkastNr:");

		// Instantiating Comboboxes
		libraryCB = new ComboBox();
                String strSQL = "select * from boek";
            libraryResult = db.getData(strSQL);
            //database opzoeken
            try {
            while (libraryResult.next()) {
                String strItem = libraryResult.getString("ISBN");
                libraryCB.getItems().add(strItem);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
            
		caseNrCB = new ComboBox();

		// Instantiating Buttons
		deleteButton = new Button("Verwijder");

		// Instantiating Text
		text = new Text("Boekenkast");

		// Make-up for text and layout
		text.setStyle("-fx-font: 17 arial");
		this.setPadding(new Insets(5, 5, 5, 5));
		this.setHgap(5);
		this.setVgap(5);

		// Placing Textobjects
		this.add(text, 0, 0);

		// Place labelobjects
		this.add(libraryLabel, 0, 1);
		this.add(caseNrLabel, 0, 2);
		this.add(errorLabel, 0, 4);

		// Placing ComboBox
		this.add(libraryCB, 1, 1);
		this.add(caseNrCB, 1, 2);

		// Placing Buttons
		this.add(deleteButton, 0, 3);

		// Add this gridpane to mainpane
		mainPane.getChildren().add(this);
	}
}