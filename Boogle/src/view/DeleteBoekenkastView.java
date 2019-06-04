package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.BoekenkastModel;
import model.Database;

public class DeleteBoekenkastView extends GridPane {
	// Declaring variables
	private final Label libraryLabel, caseNrLabel, errorLabel, errorvalueLabel;
        
	private final ComboBox libraryCB, caseNrCB;
	private final Button deleteButton,valueButton;
	private final Text text;
        private Database db = new Database();

	public DeleteBoekenkastView(Pane mainPane) {
		// Instantiating Labels
		libraryLabel = new Label("Bibliotheek:");
		errorLabel = new Label("");
		caseNrLabel = new Label("BoekenkastNr:");
                errorvalueLabel = new Label("");

		// Instantiating Comboboxes
		libraryCB = new ComboBox(); 
                setCaseNrCB();
            
		caseNrCB = new ComboBox();

		// Instantiating Buttons
		deleteButton = new Button("Verwijder");
                deleteButton.setOnAction(event -> {
                        //give error if it fails
                	if (deleteBoekenkastItems() == 0) {
				errorLabel.setText("Het verwijderen is mislukt");
			} else {
				errorLabel.setText("Het is verwijderd van de database");
			}
                });
                
                
                valueButton = new Button("Haal op");
                valueButton.setOnAction(event -> {
			setLibraryItems();
                        setBoekenkastCB();
			errorvalueLabel.setText("Data is opgehaald");
                });

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
                this.add(errorvalueLabel, 0 ,5);

		// Placing ComboBox
		this.add(libraryCB, 1, 1);
		this.add(caseNrCB, 1, 2);

		// Placing Buttons
		this.add(deleteButton, 0, 3);
                this.add(valueButton, 2, 1);

		// Add this gridpane to mainpane
		mainPane.getChildren().add(this);
	}
                        
        // get bookcasenumber from database                
        private void setBoekenkastCB() {
		caseNrCB.getItems().clear();
		for (BoekenkastModel boekenkast : db.getAllBoekenkastvalue(libraryCB.getValue().toString())) {
			caseNrCB.getItems().add(boekenkast.getBookCaseNr());
		}
	}
        //get libraryname from database
        private void setCaseNrCB() {
		libraryCB.getItems().clear();
		for (BoekenkastModel boekenkast : db.getAllBoekenkasten()) {
			libraryCB.getItems().add(boekenkast.getLibraryName());
		}
	} 
        //get items from database
        private void setLibraryItems() {
		BoekenkastModel bm = new BoekenkastModel();
		bm = db.getBoekenkastFromName(libraryCB.getValue().toString());
	}
        //delete from database
        private int deleteBoekenkastItems() {
            BoekenkastModel deleteBoekenkast = new BoekenkastModel();
            String libraryName = libraryCB.getValue().toString();
            String bookCaseNr = caseNrCB.getValue().toString();
            //translate integer to string
            int parsedBookCaseNr = Integer.parseInt(bookCaseNr);
            deleteBoekenkast.setBookCaseNr(parsedBookCaseNr);
                       return (db.deleteBoekenkast(libraryName, bookCaseNr));
        }
}