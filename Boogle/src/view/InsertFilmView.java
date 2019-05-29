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
import model.ActeurModel;
import model.Database;
import model.FilmrekModel;
import model.GenreModel;

public class InsertFilmView extends GridPane{
    private Label lblTitle, lblMovienumber, lblReg, lblDate, lblDesc, lblGenre, lblActeur, lblLibary, lblNumber, lblErrorvalue;
    private TextField txtTitle, txtMovienumber, txtReg, txtDate;
    private ComboBox boxGenre, boxActeur, boxLibary, boxNumber;
    private Text movieInput;
    private TextArea txtDesc;
    private Button btnSave, btnValue;
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
            
            txtTitle = new TextField();
            txtMovienumber = new TextField();
            txtReg = new TextField();
            txtDate = new TextField();
            txtDesc  = new TextArea();
            txtDesc.setPrefHeight(150);
            txtDesc.setPrefWidth(20);
            
            movieInput = new Text(" Film invoeren ");
            
            btnSave = new Button(" Opslaan ");
            
            btnValue = new Button("Haal op");
            btnValue.setOnAction(event -> {
                        setFilmrekvalueCB();
			setLibraryItems();
			lblErrorvalue.setText("Data is opgehaald");
		});
            
            
            setPadding(new Insets(10,10,10,10));
            setHgap(10);
            setVgap(10);
            
            boxNumber = new ComboBox();
            
            boxLibary = new ComboBox();
            setFilmrekCB();
            
            boxGenre = new ComboBox();
            setGenreCB();
            
            boxActeur = new ComboBox();
            setActorCB();
   
        
            add(movieInput, 0, 0); 
            add(btnValue, 2, 1);
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
        private void setGenreCB() {
		for (GenreModel genre : db.getAllGenres()) {
			boxGenre.getItems().add(genre.getGenreName());
		}
	}  
        private void setFilmrekCB() {
		boxLibary.getItems().clear();
		for (FilmrekModel filmrek : db.getAllFilmrekken()) {
			boxLibary.getItems().add(filmrek.getLibraryName());
		}
	}
        private void setActorCB() {
		boxActeur.getItems().clear();
		for (ActeurModel actor : db.getAllActors()) {
			boxActeur.getItems().add(actor.getName());
		}
	}
        private void setFilmrekvalueCB() {
		boxNumber.getItems().clear();
		for (FilmrekModel filmrekvalue : db.getAllFilmrekkenvalue(boxLibary.getValue().toString())) {
			boxNumber.getItems().add(filmrekvalue.getRackNr());
		}
	}
        private void setLibraryItems() {
		FilmrekModel bm = new FilmrekModel();
		bm = db.getFilmrekFromName(boxLibary.getValue().toString());
	}
}


