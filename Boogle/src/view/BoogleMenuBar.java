//menubar
package view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;

public class BoogleMenuBar extends MenuBar {

    private Menu mToevoegen, mAanpassen, mVerwijderen;
    private MenuItem miInsertBibliotheek, miInsertBoek, miInsertAuteur, miInsertActeur, miInsertFilm, miUpdateBibliotheek, miUpdateBoek, miUpdateFilm, miUpdateActeur, miUpdateAuteur, miDelete;

    public BoogleMenuBar(Pane mainPane) {
        mToevoegen = new Menu("Toevoegen");
        mAanpassen = new Menu("Aanpassen");
        mVerwijderen = new Menu("Verwijderen");

        miInsertBibliotheek = new MenuItem("Bibliotheek");
        miInsertBibliotheek.setOnAction(event -> {
            mainPane.getChildren().clear();
            new InsertBibliotheekView(mainPane);
        });

        miInsertBoek = new MenuItem("Boek");
        miInsertBoek.setOnAction(event -> {
            mainPane.getChildren().clear();
            new InsertBoekView(mainPane);
        });

        miInsertFilm = new MenuItem("Film");
        miInsertFilm.setOnAction(event -> {
            mainPane.getChildren().clear();
            new InsertBoekView(mainPane);
        });

        miInsertActeur = new MenuItem("Acteur");
        miInsertActeur.setOnAction(event -> {
            mainPane.getChildren().clear();
            new InsertActeurView(mainPane);
        });

        miInsertAuteur = new MenuItem("Acteur");
        miInsertAuteur.setOnAction(event -> {
            mainPane.getChildren().clear();
            new InsertActeurView(mainPane);
        });

        miUpdateBibliotheek = new MenuItem("Bibliotheek");
        miUpdateBibliotheek.setOnAction(event -> {
            mainPane.getChildren().clear();
            new UpdateBibliotheekView(mainPane);
        });

        miUpdateBoek = new MenuItem("Boek");
        miUpdateBoek.setOnAction(event -> {
            mainPane.getChildren().clear();
            new UpdateBoekView(mainPane);
        });

        miUpdateFilm = new MenuItem("Film");
        miUpdateFilm.setOnAction(event -> {
            mainPane.getChildren().clear();
            new UpdateBoekView(mainPane);
        });

        miUpdateActeur = new MenuItem("Acteur");
        miUpdateActeur.setOnAction(event -> {
            mainPane.getChildren().clear();
            new UpdateActeurView(mainPane);
        });

        miUpdateAuteur = new MenuItem("Auteur");
        miUpdateAuteur.setOnAction(event -> {
            mainPane.getChildren().clear();
            new UpdateActeurView(mainPane);
        });

        miDelete = new MenuItem("Verwijderen");
        miDelete.setOnAction(event -> {
            mainPane.getChildren().clear();
            new DeleteView(mainPane);
        });

        //voeg item toe aan menu
        mToevoegen.getItems().addAll(miInsertBibliotheek, miInsertBoek, miInsertFilm, miInsertActeur, miInsertAuteur);
        mAanpassen.getItems().addAll(miUpdateBibliotheek, miUpdateBoek, miUpdateFilm, miUpdateActeur, miUpdateAuteur);
        mVerwijderen.getItems().addAll(miDelete);

        //voeg menu toe aan balk
        getMenus().addAll(mToevoegen);
        getMenus().addAll(mAanpassen);
        getMenus().addAll(mVerwijderen);

    }
}
