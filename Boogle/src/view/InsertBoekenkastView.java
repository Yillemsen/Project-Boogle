package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.BibliotheekModel;
import model.BoekenkastModel;
import model.Database;

public class InsertBoekenkastView extends GridPane {
    
    private Label lblLibrary, lblCaseNr, lblError;
    private TextField txtCaseNr;
    private ComboBox boxLibrary;
    private Text acteurInput;
    private Button btnSave;
    private Database db = new Database();

    public InsertBoekenkastView(Pane mainPane) { 
        //text
        acteurInput = new Text(" Boekenkast Invoeren ");
        
        //textfield
        txtCaseNr = new TextField();
        
        //labels
        lblLibrary = new Label(" bibliotheek : ");
        lblCaseNr = new Label(" kastnummer : ");
        lblError = new Label("");
        
        //combobox
        boxLibrary = new ComboBox();
        setLibraryCB();

        //button with function
        btnSave = new Button(" Opslaan ");
        btnSave.setOnAction(event -> {
                        //give error if it fails
			if (insertBoekenkastItems() == 0) {
				lblError.setText("Het toevoegen is mislukt");
			} else {
				lblError.setText("Het is toegevoegd aan de database");
			}
			
		});
        
        //gives a view a bit space
        setPadding(new Insets(10,10,10,10));
        setHgap(10);
        setVgap(10);
        
        //put thing into place
        add(acteurInput, 0, 0); 
        add(lblLibrary, 0, 2);
        add(boxLibrary, 1, 2);
        add(lblCaseNr, 0, 3);
        add(txtCaseNr, 1, 3);
        add(btnSave, 1, 4);
        add(lblError, 1, 5);
        
        mainPane.getChildren().add(this);
        
    }
    //insert into database
    private int insertBoekenkastItems() {
            BoekenkastModel insertBoekenkast = new BoekenkastModel();
            String libraryName = boxLibrary.getValue().toString();
            String bookCaseNr = txtCaseNr.getText();
            //translate integer into string
            int parsedBookCaseNr = Integer.parseInt(bookCaseNr);
            insertBoekenkast.setBookCaseNr(parsedBookCaseNr);
            return (db.newBoekenkast(libraryName, bookCaseNr));
    }
    //get libraryName from database
    private void setLibraryCB() {
		for (BibliotheekModel library : db.getAllLibraries()) {
			boxLibrary.getItems().add(library.getName());
		}
	} 

}