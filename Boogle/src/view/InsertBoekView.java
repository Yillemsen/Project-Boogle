package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.Database;

public class InsertBoekView extends GridPane {
    private Label lblIsbn, lblBooknumber, lblTitle, lblLanguage, lblDate, lblIntTitle, lblGenre, lblAuteur, lblLibary, lblNumber, lblDesc;
    private TextField txtIsbn, txtBooknumber, txtTitle, txtDate, txtIntTitle;
    private ComboBox boxLanguage, boxGenre, boxAuteur, boxLibary, boxNumber;
    private Text bookInput;
    private TextArea txtDesc;
    private Button btnSave;
    private Database db;
    
	public InsertBoekView(Pane mainPane) {
            //Labels to show
            lblLibary = new Label (" Bibliotheek : ");
            lblNumber = new Label (" Kastnummer : ");
            lblIsbn = new Label(" ISBN : ");
            lblBooknumber = new Label(" Boeknummer : ");
            lblTitle = new Label(" Titel : ");
            lblLanguage = new Label(" Taal : ");
            lblDate = new Label(" Datum uitgave : ");
            lblIntTitle = new Label(" Internationale titel : ");
            lblDesc = new Label(" Beschrijving : ");
            lblGenre = new Label(" Genre : ");
            lblAuteur = new Label(" Auteur : ");
            db = new Database();
            
            
            //TextFields and ComboBoxs to show
            boxLibary = new ComboBox();
            boxNumber = new ComboBox();
            txtIsbn = new TextField();
            txtBooknumber = new TextField();
            txtTitle = new TextField();
            boxLanguage = new ComboBox();
            txtDate  = new TextField();
            txtIntTitle = new TextField();
            txtDesc  = new TextArea();
            txtDesc.setPrefHeight(150);
            txtDesc.setPrefWidth(20);
            boxGenre = new ComboBox();
            boxAuteur = new ComboBox();
            
            bookInput = new Text(" Boek invoeren ");
            
            btnSave = new Button(" Opslaan ");
            
            setPadding(new Insets(10,10,10,10));
            setHgap(10);
            setVgap(10);
        
            add(bookInput, 0, 0); 
            add(lblLibary, 0, 2);
            add(boxLibary, 1, 2);
            add(lblNumber, 0, 3);
            add(boxNumber, 1, 3);
            add(lblIsbn, 0, 4);
            add(txtIsbn, 1, 4);
            add(lblBooknumber, 0, 5);
            add(txtBooknumber, 1, 5);
            add(lblTitle, 0, 6);
            add(txtTitle, 1, 6);
            add(lblLanguage, 0, 7);
            add(boxLanguage, 1, 7);
            add(lblDate, 0, 8);
            add(txtDate, 1, 8);
            add(lblIntTitle, 0, 9);
            add(txtIntTitle, 1, 9);
            add(lblDesc, 0, 10);
            add(txtDesc, 1, 10);
            add(lblGenre, 0, 11);
            add(boxGenre, 1, 11);
            add(lblAuteur, 0, 12);
            add(boxAuteur, 1, 12);
            add(btnSave, 1, 13);
            System.out.println(db.getBoek("214748"));

            mainPane.getChildren().add(this);
	}
}