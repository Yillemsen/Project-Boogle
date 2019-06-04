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
import model.AuteurModel;
import model.BoekenkastModel;
import model.Database;
import model.GenreModel;

public class InsertBoekView extends GridPane {
    private Label lblIsbn, lblTitle, lblLanguage, lblDate, lblIntTitle, lblGenre, lblAuteur, lblLibary, lblNumber, lblDesc, errorvalueLabel, lblBooknumber;
    private TextField txtIsbn, txtTitle, txtDate, txtIntTitle, txtBooknumber;
    private ComboBox boxLanguage, boxGenre, boxAuteur, boxLibary, boxNumber;
    private Text bookInput;
    private TextArea txtDesc;
    private Button btnSave, chooseImageButton, valueButton;
    private String B64STRING;
    private Database db = new Database();
    
	public InsertBoekView(Pane mainPane) {
            //Labels to show
            lblLibary = new Label (" Bibliotheek : ");
            lblNumber = new Label (" Kastnummer : ");
            lblIsbn = new Label(" ISBN : ");;
            lblTitle = new Label(" Titel : ");
            lblLanguage = new Label(" Taal : ");
            lblDate = new Label(" Datum uitgave : ");
            lblIntTitle = new Label(" Internationale titel : ");
            lblDesc = new Label(" Beschrijving : ");
            lblGenre = new Label(" Genre : ");
            lblAuteur = new Label(" Auteur : ");
            lblBooknumber = new Label(" Boeknummer : ");
            errorvalueLabel = new Label("");
            
            //TextFields to show
            boxNumber = new ComboBox();
            txtIsbn = new TextField();
            txtTitle = new TextField();
            txtDate  = new TextField();
            txtIntTitle = new TextField();
            txtDesc  = new TextArea();
            txtDesc.setPrefHeight(150);
            txtDesc.setPrefWidth(20);
            txtBooknumber = new TextField();
            
            //text
            bookInput = new Text(" Boek invoeren ");
                        
            //button with function
            btnSave = new Button(" Opslaan ");
            btnSave.setOnAction(event -> {
            //insert into database
            String ISBN = txtIsbn.getText();
            String title = txtTitle.getText();
            String language = boxLanguage.getValue().toString();
            String releaseDate = txtDate.getText();
            String intTitle = txtIntTitle.getText();
            String description = txtDesc.getText();
            String genre = boxGenre.getValue().toString();
            String image = B64STRING = null;
            String auteur = boxAuteur.getValue().toString();
            String booknumber = txtBooknumber.getText();
            String rackNr = boxNumber.getValue().toString();
            String library = boxLibary.getValue().toString();
                       db.newBoek(ISBN, title, language, releaseDate, intTitle, description, genre, image);
                       db.newBoekheeftauteur(ISBN, auteur);
                       db.newBoekenkastheeftboek(rackNr, library, ISBN, booknumber);
            });
            
            //put things into place
            setPadding(new Insets(10,10,10,10));
            setHgap(10);
            setVgap(10);
            
            //comboBox
            boxLibary = new ComboBox();
            setCaseNrCB();

            boxGenre = new ComboBox();
            setGenreCB();

            boxAuteur = new ComboBox();
            setAuthorCB();
            
            boxLanguage = new ComboBox();
            boxLanguage.getItems().addAll("Nederlands", "English", "Francias","Polski","Deutsch");
            
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
                
                //button with function
                valueButton = new Button("Haal op");
                valueButton.setOnAction(event -> {
			setLibraryItems();
                        setBoekenkastCB();
			errorvalueLabel.setText("Data is opgehaald");
                });
                
            // put things into place
            add(bookInput, 0, 0); 
            add(lblLibary, 0, 2);
            add(boxLibary, 1, 2);
            add(valueButton, 2, 2);
            add(errorvalueLabel, 2, 1);
            add(lblBooknumber, 0, 4);
            add(txtBooknumber, 1, 4);
            add(lblNumber, 0, 3);
            add(boxNumber, 1, 3);
            add(lblIsbn, 0, 5);
            add(txtIsbn, 1, 5);
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
            add(chooseImageButton, 1, 13);
            add(btnSave, 1, 14);

            mainPane.getChildren().add(this);
            
	}
        //get authorname from database
        private void setAuthorCB() {
		for (AuteurModel author : db.getAllAuthors()) {
			boxAuteur.getItems().add(author.getName());
		}
	}
        //get genrename from database
        private void setGenreCB() {
		for (GenreModel genre : db.getAllGenres()) {
			boxGenre.getItems().add(genre.getGenreName());
		}
	}  
        //get items from database
        private void setLibraryItems() {
		BoekenkastModel bm = new BoekenkastModel();
		bm = db.getBoekenkastFromName(boxLibary.getValue().toString());
	}
        // get items from previous combobox
        private void setBoekenkastCB() {
		boxNumber.getItems().clear();
		for (BoekenkastModel boekenkast : db.getAllBoekenkastvalue(boxLibary.getValue().toString())) {
			boxNumber.getItems().add(boekenkast.getBookCaseNr());
		}
	}
        // get libraryName from database
        private void setCaseNrCB() {
		boxLibary.getItems().clear();
		for (BoekenkastModel boekenkast : db.getAllBoekenkasten()) {
			boxLibary.getItems().add(boekenkast.getLibraryName());
		}
	} 
        
}