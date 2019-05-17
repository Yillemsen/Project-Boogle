package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class UpdateFilmView extends GridPane{
	private final Label selectTitleLabel, libraryLabel, filmRackNrLabel, genreLabel, titleLabel, filmNrLabel, directorLabel, releaseDateLabel, actorLabel, errorLabel;
	private final TextField titleTextField, filmNrTextField, directorTextField, releaseDateTextField, actorTextField;
	private final Button saveButton, addFilmRackButton, addActorTextFieldButton;
	private final Text text;
	private final ComboBox titleCB, libraryCB, filmRackCB, genreCB;

	public UpdateFilmView(Pane mainPane) {
		// Instantiating labelobjects
				selectTitleLabel = new Label("Selecteer titel:");
				libraryLabel = new Label("Bibliotheek:");
				filmRackNrLabel = new Label("Filmrek:");
				genreLabel = new Label("Genre:");
				titleLabel = new Label("Titel:");
				filmNrLabel = new Label("FilmNr:");
				directorLabel = new Label("Regiseur:");
				releaseDateLabel = new Label("Datum uitgave:");
				actorLabel = new Label("Acteur:");
				errorLabel = new Label("Error, niet alle velden zijn (correct) ingevuld");

				// Instantiating textfieldobjects
				titleTextField = new TextField();
				filmNrTextField = new TextField();
				directorTextField = new TextField();
				releaseDateTextField = new TextField();
				actorTextField = new TextField();
				
				// Instantiating buttonobjects
				saveButton = new Button("Opslaan");
				addFilmRackButton = new Button("+");
				addActorTextFieldButton = new Button("+");

				// Instantiating textobjects
				text = new Text("Film aanpassen");
				
				//Instantiating comboboxes
				titleCB = new ComboBox();
				libraryCB = new ComboBox();
				filmRackCB = new ComboBox();
				genreCB = new ComboBox();

				// Make-up for text and layout
				text.setStyle("-fx-font: 17 arial");
				setPadding(new Insets(5, 5, 5, 5));
				setHgap(5);
				setVgap(5);

				// Place textobject
				this.add(text, 0, 0);

				// Place labelobjects with for loop
				Label[] labelObjects = { selectTitleLabel, libraryLabel, filmRackNrLabel, genreLabel, titleLabel, filmNrLabel, directorLabel, releaseDateLabel, actorLabel };
				for (int i = 0; i < labelObjects.length; i++) {
					this.add(labelObjects[i], 0, i + 1);
				}

				// Place textfieldobjects with for loop
				TextField[] textFieldObjects = { titleTextField, filmNrTextField, directorTextField, releaseDateTextField, actorTextField };
				for (int i = 0; i < textFieldObjects.length; i++) {
					this.add(textFieldObjects[i], 1, i + 5);
				}
				
				//Place comboboxes
				this.add(titleCB, 1, 1);
				this.add(libraryCB, 1, 2);
				this.add(filmRackCB, 1, 3);
				this.add(genreCB, 1, 4);

				// Place buttonobject
				this.add(saveButton, 1, 10);
				this.add(addFilmRackButton, 2, 3);
				this.add(addActorTextFieldButton, 2, 9);

				// Place errorlabelobject
				this.add(errorLabel, 1, 11);

				mainPane.getChildren().add(this);
	}
}
