package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.Database;

public class InsertActeurView extends GridPane {
    
    private Label lblName, lblBirth, lblDeath, lblError;
    private TextField txtName, txtBirth, txtDeath;
    private Text acteurInput;
    private Button btnSave;
    private Database db = new Database();

    public InsertActeurView(Pane mainPane) {
        //text
        acteurInput = new Text(" Acteur invoeren ");
        
        //textfields
        txtName = new TextField();
        txtBirth = new TextField();
        txtDeath = new TextField();
        
        //labels
        lblName = new Label(" Naam : ");
        lblBirth = new Label(" Geboortedatum : ");
        lblDeath = new Label (" Overlijdingsdatum : ");
        lblError = new Label("");

        //button with function
        btnSave = new Button(" Opslaan ");
        btnSave.setOnAction(event -> {
			if (insertActeurItems() == 0) {
                            //give error if it fails
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
        add(acteurInput, 0, 0); 
        add(lblName, 0, 2);
        add(txtName, 1, 2);
        add(lblBirth, 0, 3);
        add(txtBirth, 1, 3);
        add(lblDeath, 0, 4);
        add(txtDeath, 1, 4);
        add(btnSave, 1, 5);
        add(lblError, 1, 6);
        
        mainPane.getChildren().add(this);
        
    }
    //insert into databse
    private int insertActeurItems() {
            String name = txtName.getText();
            String birth = txtBirth.getText();
            String death = txtDeath.getText();
            return (db.newActeur(name, birth, death));
    }

}