package boogle;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class InsertBoekFilmView extends GridPane {
    private Label lblIsbn, lblTitle, lblBookNumber, lblLanguage, lblDate, lblIntTitle, lblGenre;
    private TextField txtIsbn, txtTitle, txtBookNumber, txtLanguage, txtDate, txtIntTitle, txtGenre;
    private Text bookInput;
    private Button btnSave;

    public InsertBoekFilmView(Pane mainPane) { 
        lblIsbn = new Label(" ISBN : ");
        lblTitle = new Label (" Titel ; ");
        lblBookNumber = new Label (" Boeknummer : ");
        lblLanguage = new Label (" Taal : ");
        lblDate = new Label (" Datum uitgave : ");
        lblIntTitle = new Label (" Internationale Titel : ");
        lblGenre = new Label (" Genre naam");
        
        txtIsbn = new TextField();
        txtTitle = new TextField();
        txtBookNumber = new TextField();
        txtLanguage = new TextField();
        txtDate = new TextField();
        txtIntTitle = new TextField();
        txtGenre = new TextField();
        
        bookInput = new Text(" Boek invoeren ");
        
        btnSave = new Button("Opslaan");
        
        setPadding(new Insets(10,10,10,10));
        setHgap(10);
        setVgap(10);
        
        add(bookInput, 0, 0); 
        add(lblIsbn, 0, 2);
        add(txtIsbn, 1, 2);
        add(lblTitle, 0, 3);
        add(txtTitle, 1, 3);
        add(lblBookNumber, 0, 4);
        add(txtBookNumber, 1, 4);
        add(lblLanguage, 0, 5);
        add(txtLanguage, 1, 5);
        add(lblDate, 0, 6);
        add(txtDate, 1, 6);
        add(lblIntTitle, 0, 7);
        add(txtIntTitle, 1, 7);
        add(lblGenre, 0, 8);
        add(txtGenre, 1, 8);
        add(btnSave, 1, 9);
        
        mainPane.getChildren().add(this);
        
    }}