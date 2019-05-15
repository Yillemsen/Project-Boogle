//menubar
package boogle;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;

public class BoogleMenuBar extends MenuBar {

    private Menu mToevoegen, mAanpassen, mVerwijderen;
    private MenuItem miInsertBibliotheek, miInsertBoekFilm, miInsertActeurAuteur, miUpdateBibliotheek, miUpdateBoekFilm, miUpdateActeurAuteur, miDelete;

    public BoogleMenuBar(Pane mainPane) {
        mToevoegen = new Menu("Toevoegen");
        mAanpassen = new Menu("Aanpassen");
        mVerwijderen = new Menu("Verwijderen");

        miInsertBibliotheek = new MenuItem("Bibliotheek");
        miInsertBibliotheek.setOnAction(event -> {
            mainPane.getChildren().clear();
            new InsertBibliotheekView(mainPane);
        });

        miInsertBoekFilm = new MenuItem("Boek / Film");
        miInsertBoekFilm.setOnAction(event -> {
            mainPane.getChildren().clear();
            new InsertBoekFilmView(mainPane);
        });

        miInsertActeurAuteur = new MenuItem("Acteur / Auteur");
        miInsertActeurAuteur.setOnAction(event -> {
            mainPane.getChildren().clear();
            new InsertActeurAuteurView(mainPane);
        });
        
        miUpdateBibliotheek = new MenuItem("Bibliotheek");
        miUpdateBibliotheek.setOnAction(event -> {
            mainPane.getChildren().clear();
            new UpdateBibliotheekView(mainPane);
        });

        miUpdateBoekFilm = new MenuItem("Boek / Film");
        miUpdateBoekFilm.setOnAction(event -> {
            mainPane.getChildren().clear();
            new UpdateBoekFilmView(mainPane);
        });

        miUpdateActeurAuteur = new MenuItem("Acteur / Auteur");
        miUpdateActeurAuteur.setOnAction(event -> {
            mainPane.getChildren().clear();
            new UpdateActeurAuteurView(mainPane);
        });
        
        miDelete = new MenuItem("Verwijderen");
        miDelete.setOnAction(event -> {
            mainPane.getChildren().clear();
            new DeleteView(mainPane);
        });

        //voeg item toe aan menu
        mToevoegen.getItems().addAll(miInsertBibliotheek, miInsertBoekFilm, miInsertActeurAuteur);
        mAanpassen.getItems().addAll(miUpdateBibliotheek, miUpdateBoekFilm, miUpdateActeurAuteur);
        mVerwijderen.getItems().addAll(miDelete);

        //voeg menu toe aan balk        
        getMenus().addAll(mToevoegen);
        getMenus().addAll(mAanpassen);
        getMenus().addAll(mVerwijderen);

    }
}
