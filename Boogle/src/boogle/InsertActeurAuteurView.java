package boogle;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class InsertActeurAuteurView extends GridPane {
    
    private Label lblNameActeur, lblNameAuteur, lblBirthActeur, lblBirthAuteur, lblDeathActeur, lblDeathAuteur;
    private TextField txtNameActeur, txtNameAuteur, txtBirthActeur, txtBirthAuteur, txtDeathActeur, txtDeathAuteur;
    private Text acteurInput, auteurInput;
    private Button btnActeur, btnAuteur;

    public InsertActeurAuteurView(Pane mainPane) { 
        auteurInput = new Text(" Auteur invoeren");
        acteurInput = new Text(" Acteur invoeren");
        
        txtNameActeur = new TextField();
        txtNameAuteur = new TextField();
        txtBirthActeur = new TextField();
        txtBirthAuteur = new TextField();
        txtDeathActeur = new TextField();
        txtDeathAuteur = new TextField();
        
        lblNameActeur = new Label(" Naam : ");
        lblNameAuteur = new Label(" Naam : ");
        lblBirthActeur = new Label(" Geboortedatum : ");
        lblBirthAuteur = new Label (" Geboortedatum : ");
        lblDeathActeur = new Label (" Overlijdingsdatum : ");
        lblDeathActeur = new Label (" Overlijdingsdatum : ");
        
        btnActeur = new Button(" Opslaan ");
        btnActeur = new Button(" Opslaan ");
        
    }}