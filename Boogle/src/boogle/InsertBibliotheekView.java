package boogle;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class InsertBibliotheekView extends GridPane {
    private Label lblName, lblAddress, lblPlace, lblPhone;
    private TextField txtName, txtAddress, txtPlace, txtPhone;
    private Text libaryInput;
    private Button btnSave;

    public InsertBibliotheekView(Pane mainPane) {    
        libaryInput = new Text(" Bibliotheek invoeren");
       
        txtName = new TextField();
        txtAddress = new TextField();
        txtPlace = new TextField();
        txtPhone = new TextField();
        
        lblName = new Label(" Naam : ");
        lblAddress = new Label(" Adres : ");
        lblPlace = new Label(" Plaats : ");
        lblPhone = new Label (" Telefoonnummer : ");
        
        btnSave = new Button(" Opslaan ");
        
        setPadding(new Insets(10,10,10,10));
        setHgap(10);
        setVgap(10);
        
        add(libaryInput, 0, 0); 
        add(lblName, 0, 2);
        add(txtName, 1, 2);
        add(lblAddress, 0, 3);
        add(txtAddress, 1, 3);
        add(lblPlace, 0, 4);
        add(txtPlace, 1, 4);
        add(lblPhone, 0, 5);
        add(txtPhone, 1, 5);
        add(btnSave, 1, 6);
        
        mainPane.getChildren().add(this);
        
    }}