package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class InsertFilmView extends GridPane{
    private Label lblTitle, lblMovienumber, lblReg, lblDate, lblDesc, lblGenre;
    private TextField txtTitle, txtMovienumber, txtReg, txtDate, txtGenre;
    private Text movieInput;
    private TextArea txtDesc;
    private Button btnSave;
	
	public InsertFilmView(Pane mainPane) {
            lblTitle = new Label(" Titel : ");
            lblMovienumber = new Label(" Filmnummer : ");
            lblReg = new Label(" Regiseur : ");
            lblDate = new Label(" Premiere datum : ");
            lblDesc = new Label(" Beschrijving : ");
            lblGenre = new Label(" Genre : ");
            
            txtTitle = new TextField();
            txtMovienumber = new TextField();
            txtReg = new TextField();
            txtDate = new TextField();
            txtDesc  = new TextArea();
            txtDesc.setPrefHeight(150);
            txtDesc.setPrefWidth(20);
            txtGenre = new TextField();
            
            movieInput = new Text(" Film invoeren ");
            
            btnSave = new Button(" Opslaan ");
            
            setPadding(new Insets(10,10,10,10));
            setHgap(10);
            setVgap(10);
        
            add(movieInput, 0, 0); 
            add(lblTitle, 0, 2);
            add(txtTitle, 1, 2);
            add(lblMovienumber, 0, 3);
            add(txtMovienumber, 1, 3);
            add(lblReg, 0, 4);
            add(txtReg, 1, 4);
            add(lblDate, 0, 5);
            add(txtDate, 1, 5);
            add(lblDesc, 0, 6);
            add(txtDesc, 1, 6);
            add(lblGenre, 0, 7);
            add(txtGenre, 1, 7);
            add(btnSave, 1, 8);

            mainPane.getChildren().add(this);
		
	}

}
