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
import model.FilmrekModel;

public class InsertFilmRekView extends GridPane {
    
    private Label lblLibrary, lblRackNr, lblError;
    private TextField txtCaseNr;
    private ComboBox boxLibrary;
    private Text acteurInput;
    private Button btnSave;
    private Database db = new Database();

    public InsertFilmRekView(Pane mainPane) { 
        acteurInput = new Text(" Filmrek Invoeren ");
        
        txtCaseNr = new TextField();
        
        lblLibrary = new Label(" bibliotheek : ");
        lblRackNr = new Label(" reknummer : ");
        lblError = new Label("");
        
        boxLibrary = new ComboBox();
        setLibraryCB();

        btnSave = new Button(" Opslaan ");
        btnSave.setOnAction(event -> {
			if (insertFilmrekItems() == 0) {
				lblError.setText("Het toevoegen is mislukt");
			} else {
				lblError.setText("Het is toegevoegd aan de database");
			}
			
		});
        
        setPadding(new Insets(10,10,10,10));
        setHgap(10);
        setVgap(10);
        
        add(acteurInput, 0, 0); 
        add(lblLibrary, 0, 2);
        add(boxLibrary, 1, 2);
        add(lblRackNr, 0, 3);
        add(txtCaseNr, 1, 3);
        add(btnSave, 1, 4);
        add(lblError, 1, 5);
        
        mainPane.getChildren().add(this);
        
    }
    private int insertFilmrekItems() {
            FilmrekModel insertFilmrek = new FilmrekModel();
            String libraryName = boxLibrary.getValue().toString();
            String RackNr = txtCaseNr.getText();
            int parsedRackNr = Integer.parseInt(RackNr);
            insertFilmrek.setRackNr(parsedRackNr);
            return (db.newFilmrek(libraryName, RackNr));
    }
    
    private void setLibraryCB() {
		for (BibliotheekModel library : db.getAllLibraries()) {
			boxLibrary.getItems().add(library.getName());
		}
	} 

}