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
    
    private Label lblName, lblBirth, lblDeath;
    private TextField txtName, txtBirth, txtDeath;
    private Text acteurInput;
    private Button btnSave;
    private Database db = new Database();

    public InsertActeurView(Pane mainPane) { 
        acteurInput = new Text(" Acteur invoeren ");
        
        txtName = new TextField();
        txtBirth = new TextField();
        txtDeath = new TextField();
        
        lblName = new Label(" Naam : ");
        lblBirth = new Label(" Geboortedatum : ");
        lblDeath = new Label (" Overlijdingsdatum : ");

        btnSave = new Button(" Opslaan ");
        btnSave.setOnAction(event -> {
            
            String name = txtName.getText();
            String birth = txtBirth.getText();
            String death = txtDeath.getText();
                       db.newActeur(name, birth, death);
                       
        });
        
        setPadding(new Insets(10,10,10,10));
        setHgap(10);
        setVgap(10);
        
        add(acteurInput, 0, 0); 
        add(lblName, 0, 2);
        add(txtName, 1, 2);
        add(lblBirth, 0, 3);
        add(txtBirth, 1, 3);
        add(lblDeath, 0, 4);
        add(txtDeath, 1, 4);
        add(btnSave, 1, 5);
        
        mainPane.getChildren().add(this);
        
    }

}