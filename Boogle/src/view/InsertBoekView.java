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
import model.BibliotheekModel;
import model.Database;

public class InsertBoekView extends GridPane {
    private Label lblIsbn, lblBooknumber, lblTitle, lblLanguage, lblDate, lblIntTitle, lblGenre, lblAuteur, lblLibary, lblNumber, lblDesc;
    private TextField txtIsbn, txtBooknumber, txtTitle, txtDate, txtIntTitle;
    private ComboBox boxLanguage, boxGenre, boxAuteur, boxLibary, boxNumber;
    private Text bookInput;
    private TextArea txtDesc;
    private Button btnSave, btnPlus;
    private ResultSet biebResult, genreResult, auteurResult;
    private BibliotheekModel biebName;
    private Database db = new Database();
    
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
            
            
            //TextFields to show
            boxNumber = new ComboBox();
            txtIsbn = new TextField();
            txtBooknumber = new TextField();
            txtTitle = new TextField();
            txtDate  = new TextField();
            txtIntTitle = new TextField();
            txtDesc  = new TextArea();
            txtDesc.setPrefHeight(150);
            txtDesc.setPrefWidth(20);
            
            bookInput = new Text(" Boek invoeren ");
            
            btnPlus = new Button("+");
          //  btnPlus.setOnAction(event -> {
            //    biebName = new BibliotheekModel();
             //   biebName.setName(boxLibary.getSelectionModel().getSelectedItem().toString());
             //   try { String strSQL = "select * from bibliotheek where naam=" + biebName.getName() + "";
                    
              //  }
             //    catch (Exception e) {
               // System.err.println(e.getMessage());
                
            //});
            
            btnSave = new Button(" Opslaan ");
            String isbn = txtIsbn.getText();
            String boeknummer = txtBooknumber.getText();
            String titel = txtTitle.getText();
            String taal = boxLanguage.getSelectedItem().toString();
            String datumuitgave = txtDate.getText();
            String inttitel = txtIntTitle.getText();
            String beschrijving = txtDesc.getText();
            String genre = boxGenre.getText();
            String auteur = boxAuteur.getText();
                       db.newBibliotheek(name, address, place, phone);
            
            setPadding(new Insets(10,10,10,10));
            setHgap(10);
            setVgap(10);
            
            /**
             * functie SQL statements
             */
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
            
            boxAuteur = new ComboBox();
            String strSQL2 = "select * from auteur";
            auteurResult = db.getData(strSQL2);
            //database opzoeken
            try {
            while (auteurResult.next()) {
                String strItem2 = auteurResult.getString("naam");
                boxAuteur.getItems().add(strItem2);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
            
            
            
            boxLanguage = new ComboBox();
            boxLanguage.getItems().addAll("Nederlands", "English", "Francias","Polski","Deutsch");
        
            add(bookInput, 0, 0); 
            add(lblLibary, 0, 2);
            add(boxLibary, 1, 2);
            add(lblNumber, 0, 3);
            add(boxNumber, 1, 3);
            //add(btnPlus, 2, 3);
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

            mainPane.getChildren().add(this);
	}
}