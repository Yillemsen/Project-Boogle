package view;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
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
    private Button btnSave, btnValue, chooseImageButton;
    private String B64STRING;
    private Database db = new Database();
	
	public InsertFilmView(Pane mainPane) {
            //labels
            lblLibary = new Label (" Bibliotheek : ");
            lblNumber = new Label (" Kastnummer : ");
            lblTitle = new Label(" Titel : ");
            lblMovienumber = new Label(" Filmnummer : ");
            lblReg = new Label(" Regiseur : ");
            lblDate = new Label(" Premiere datum : ");
            lblDesc = new Label(" Beschrijving : ");
            lblGenre = new Label(" Genre : ");
            lblActeur = new Label(" Acteur : ");
            lblErrorvalue = new Label("");

            //textfield
            txtTitle = new TextField();
            txtMovienumber = new TextField();
            txtReg = new TextField();
            txtDate = new TextField();
            txtDesc  = new TextArea();
            txtDesc.setPrefHeight(150);
            txtDesc.setPrefWidth(20);
            
            //text
            movieInput = new Text(" Film invoeren ");

            //button with function
            btnValue = new Button("Haal op");
            btnValue.setOnAction(event -> {
                        setFilmrekvalueCB();
			setLibraryItems();
			lblErrorvalue.setText("Data is opgehaald");
		});
            
            //comboBox
            boxNumber = new ComboBox();
            
            boxLibary = new ComboBox();
            setFilmrekCB();
            
            boxGenre = new ComboBox();
            setGenreCB();
            
            boxActeur = new ComboBox();
            setActorCB();
   
            //button with function
            btnSave = new Button(" Opslaan ");
            btnSave.setOnAction(event -> {
            //insert into database
            String title = txtTitle.getText();
            String director = txtReg.getText();
            String releaseDate = txtDate.getText();
            String description = txtDesc.getText();
            String genreName = boxGenre.getValue().toString();
            String image = B64STRING = null;
            String acteur = boxActeur.getValue().toString();
            String rackNr = boxNumber.getValue().toString();
            String library = boxLibary.getValue().toString();
            String movienumber = txtMovienumber.getText();
                       db.newFilm(title, director, releaseDate, genreName, image, description);
                       db.newFilmheeftacteur(title, acteur);
                       db.newFilmrekheeftfilm(rackNr, library, title, movienumber);
            });
            
            // Set onactionlistener for chooseImageButton
                chooseImageButton = new Button("Kies afbeelding");
		chooseImageButton.setOnAction(event -> {
			// Create a filechooser
			FileChooser fileChooser = new FileChooser();

			// set filter so that you can select PNG images
			FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
			fileChooser.getExtensionFilters().addAll(extFilterPNG);
			// Open filechooser
			File file = fileChooser.showOpenDialog(null);

			try {
				// Read image into a bufferedImage
				BufferedImage bufferedImage = ImageIO.read(file);
				ByteArrayOutputStream s = new ByteArrayOutputStream();
				ImageIO.write(bufferedImage, "png", s);
				// Convert image into a bytearray
				byte[] byteImage = s.toByteArray();
				s.close();
				// encode bytearray to B64String
				String encodedImage = Base64.getEncoder().encodeToString(byteImage);

				B64STRING = encodedImage;

				// Catch readException
			} catch (IOException e) {
				e.printStackTrace();
			}

		});
                
            //gives a view a bit space
            setPadding(new Insets(10,10,10,10));
            setHgap(10);
            setVgap(10);
       
            //put things into place
            add(movieInput, 0, 0); 
            add(lblErrorvalue, 2, 1);
            add(btnValue, 2, 2);
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
            add(chooseImageButton, 1, 11);
            add(btnSave, 1, 12);

            mainPane.getChildren().add(this);
		
	}
        //get genreName from database
        private void setGenreCB() {
		for (GenreModel genre : db.getAllGenres()) {
			boxGenre.getItems().add(genre.getGenreName());
		}
	}  
        //get libraryName from database
        private void setFilmrekCB() {
		boxLibary.getItems().clear();
		for (FilmrekModel filmrek : db.getAllFilmrekken()) {
			boxLibary.getItems().add(filmrek.getLibraryName());
		}
	}
        //get actor from databse
        private void setActorCB() {
		boxActeur.getItems().clear();
		for (ActeurModel actor : db.getAllActors()) {
			boxActeur.getItems().add(actor.getName());
		}
	}
        //get value from previous combobox
        private void setFilmrekvalueCB() {
		boxNumber.getItems().clear();
		for (FilmrekModel filmrekvalue : db.getAllFilmrekkenvalue(boxLibary.getValue().toString())) {
			boxNumber.getItems().add(filmrekvalue.getRackNr());
		}
	}
        //get items from database
        private void setLibraryItems() {
		FilmrekModel bm = new FilmrekModel();
		bm = db.getFilmrekFromName(boxLibary.getValue().toString());
	}
}


