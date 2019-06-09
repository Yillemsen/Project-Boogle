package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.Database;

public class InsertBibliotheekView extends GridPane {
    private Label lblName, lblAdres, lblLocation, lblCell, lblError;
    private TextField txtName, txtAdres, txtLocation, txtCell;
    private Text libaryInput;
    private Button btnSave;
    private Database db = new Database();

    public InsertBibliotheekView(Pane mainPane) {   
        //text
        libaryInput = new Text(" Bibliotheek invoeren ");
        
        //textfields
        txtName = new TextField();
        txtAdres = new TextField();
        txtLocation = new TextField();
        txtCell = new TextField();
        
        //labels
        lblName = new Label(" Naam : ");
        lblAdres = new Label(" Adres : ");
        lblLocation = new Label(" Plaats : ");
        lblCell = new Label (" Telefoonnummer : ");
        lblError = new Label("");
        
        
        //button
        btnSave = new Button(" Opslaan ");
        btnSave.setOnAction(event -> {
                        //give error if it fails
			if (insertLibraryItems() == 0) {
				lblError.setText("Het toevoegen is mislukt");
			} else {
				lblError.setText("Het is toegevoegd aan de database");
			}
			
		});
        
        //gives a view a bit space
        setPadding(new Insets(10,10,10,10));
        setHgap(10);
        setVgap(10);
        
        //put things in place
        add(libaryInput, 0, 0); 
        add(lblName, 0, 2);
        add(txtName, 1, 2);
        add(lblAdres, 0, 3);
        add(txtAdres, 1, 3);
        add(lblLocation, 0, 4);
        add(txtLocation, 1, 4);
        add(lblCell, 0, 5);
        add(txtCell, 1, 5);
        add(btnSave, 1, 6);
        add(lblError, 1, 7);
        
        mainPane.getChildren().add(this);
        
    }
    //insert into database
    private int insertLibraryItems() {
            String name = txtName.getText();
            String address = txtAdres.getText();
            String place = txtLocation.getText();
            String phone = txtCell.getText();
            return (db.newBibliotheek(name, address, place, phone));
    }

}