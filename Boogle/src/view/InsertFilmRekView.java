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
import model.Database;
import model.FilmrekModel;

public class InsertFilmRekView extends GridPane {
    
    private Label lblLibrary, lblRackNr, lblError;
    private TextField txtCaseNr;
    private ComboBox boxLibrary;
    private Text acteurInput;
    private Button btnSave;
    private Database db = new Database();

    public InsertFilmRekView(Pane mainPane) { 
        //text
        acteurInput = new Text(" Filmrek Invoeren ");
        
        //textfield
        txtCaseNr = new TextField();
        
        //labels
        lblLibrary = new Label(" bibliotheek : ");
        lblRackNr = new Label(" reknummer : ");
        lblError = new Label("");
        
        //combobox
        boxLibrary = new ComboBox();
        setLibraryCB();

        //button with function
        btnSave = new Button(" Opslaan ");
        btnSave.setOnAction(event -> {
            //give error if it fails
			if (insertFilmrekItems() == 0) {
				lblError.setText("Het toevoegen is mislukt");
			} else {
				lblError.setText("Het is toegevoegd aan de database");
			}
			
		});
        
        //gives a view a bit space
        setPadding(new Insets(10,10,10,10));
        setHgap(10);
        setVgap(10);
        
        //put things into place
        add(acteurInput, 0, 0); 
        add(lblLibrary, 0, 2);
        add(boxLibrary, 1, 2);
        add(lblRackNr, 0, 3);
        add(txtCaseNr, 1, 3);
        add(btnSave, 1, 4);
        add(lblError, 1, 5);
        
        mainPane.getChildren().add(this);
        
    }
    //insert into database
    private int insertFilmrekItems() {
            FilmrekModel insertFilmrek = new FilmrekModel();
            String libraryName = boxLibrary.getValue().toString();
            String RackNr = txtCaseNr.getText();
            //translate integer into string
            int parsedRackNr = Integer.parseInt(RackNr);
            insertFilmrek.setRackNr(parsedRackNr);
            return (db.newFilmrek(libraryName, RackNr));
    }
    //get libraryName from database
    private void setLibraryCB() {
		for (BibliotheekModel library : db.getAllLibraries()) {
			boxLibrary.getItems().add(library.getName());
		}
	} 

}