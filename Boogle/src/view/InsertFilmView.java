package view;

import java.sql.ResultSet;
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

public class InsertFilmView extends GridPane{
    private Label lblTitle, lblMovienumber, lblReg, lblDate, lblDesc, lblGenre, lblActeur, lblLibary, lblNumber;
    private TextField txtTitle, txtMovienumber, txtReg, txtDate;
    private ComboBox boxGenre, boxActeur, boxLibary, boxNumber;
    private ResultSet genreResult, auteurResult, biebResult;
    private Text movieInput;
    private TextArea txtDesc;
    private Button btnSave;
    private Database db = new Database();
	
	public InsertFilmView(Pane mainPane) {
            lblLibary = new Label (" Bibliotheek : ");
            lblNumber = new Label (" Kastnummer : ");
            lblTitle = new Label(" Titel : ");
            lblMovienumber = new Label(" Filmnummer : ");
            lblReg = new Label(" Regiseur : ");
            lblDate = new Label(" Premiere datum : ");
            lblDesc = new Label(" Beschrijving : ");
            lblGenre = new Label(" Genre : ");
            lblActeur = new Label(" Acteur : ");
            
            boxLibary = new ComboBox();
            boxNumber = new ComboBox();
            txtTitle = new TextField();
            txtMovienumber = new TextField();
            txtReg = new TextField();
            txtDate = new TextField();
            txtDesc  = new TextArea();
            txtDesc.setPrefHeight(150);
            txtDesc.setPrefWidth(20);
            boxGenre = new ComboBox();
            boxActeur = new ComboBox();
            
            movieInput = new Text(" Film invoeren ");
            
            btnSave = new Button(" Opslaan ");
            
            setPadding(new Insets(10,10,10,10));
            setHgap(10);
            setVgap(10);
            
            boxLibary = new ComboBox();
            String strSQL = "select * from bibliotheek";
            biebResult = db.getData(strSQL);
            //database opzoeken
            try {
            while (biebResult.next()) {
                String strItem = biebResult.getString("naam");
                boxLibary.getItems().add(strItem);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

            boxGenre = new ComboBox();
            String strSQL1 = "select * from genre";
            genreResult = db.getData(strSQL1);
            //database opzoeken
            try {
            while (genreResult.next()) {
                String strItem1 = genreResult.getString("genrenaam");
                boxGenre.getItems().add(strItem1);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
            
            boxActeur = new ComboBox();
            String strSQL2 = "select * from auteur";
            auteurResult = db.getData(strSQL2);
            //database opzoeken
            try {
            while (auteurResult.next()) {
                String strItem2 = auteurResult.getString("naam");
                boxActeur.getItems().add(strItem2);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
            add(movieInput, 0, 0); 
            add(lblLibary, 0, 2);
            add(boxLibary, 1, 2);
            add(lblNumber, 0, 3);
            add(boxNumber, 1, 3);
            add(lblTitle, 0, 4);
            add(txtTitle, 1, 4);
            add(lblMovienumber, 0, 5);
            add(txtMovienumber, 1, 5);
            add(lblReg, 0, 6);
            add(txtReg, 1, 6);
            add(lblDate, 0, 7);
            add(txtDate, 1, 7);
            add(lblDesc, 0, 8);
            add(txtDesc, 1, 8);
            add(lblGenre, 0, 9);
            add(boxGenre, 1, 9);
            add(lblActeur, 0, 10);
            add(boxActeur, 1, 10);
            add(btnSave, 1, 11);

            mainPane.getChildren().add(this);
		
	}

}
