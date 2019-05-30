//menubar
package view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;

public class BoogleMenuBar extends MenuBar {

    private Menu mToevoegen, mAanpassen, mVerwijderen;
    private MenuItem miInsertBibliotheek, miInsertBoek, miInsertAuteur, miInsertActeur, miInsertFilm, miUpdateBibliotheek, miUpdateBoek, miUpdateFilm, miUpdateActeur, miUpdateAuteur, miDeleteBoekenkast, 
    miDeleteActeur, miDeleteAuteur, miDeleteBibliotheek, miDeleteFilmrek, miDeleteFilm, miDeleteBoek, miInsertBoekenkast, miInsertFilmrek;

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
            new InsertFilmView(mainPane);
        });

        miInsertActeur = new MenuItem("Acteur");
        miInsertActeur.setOnAction(event -> {
            mainPane.getChildren().clear();
            new InsertActeurView(mainPane);
        });

        miInsertAuteur = new MenuItem("Auteur");
        miInsertAuteur.setOnAction(event -> {
            mainPane.getChildren().clear();
            new InsertAuteurView(mainPane);
        });
        
        miInsertBoekenkast = new MenuItem("Boekenkast");
        miInsertBoekenkast.setOnAction(event -> {
            mainPane.getChildren().clear();
            new InsertBoekenkastView(mainPane);
        });
        
        miInsertFilmrek = new MenuItem("Filmrek");
        miInsertFilmrek.setOnAction(event -> {
            mainPane.getChildren().clear();
            new InsertFilmRekView(mainPane);
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
            new UpdateFilmView(mainPane);
        });

        miUpdateActeur = new MenuItem("Acteur");
        miUpdateActeur.setOnAction(event -> {
            mainPane.getChildren().clear();
            new UpdateActeurView(mainPane);
        });

        miUpdateAuteur = new MenuItem("Auteur");
        miUpdateAuteur.setOnAction(event -> {
            mainPane.getChildren().clear();
            new UpdateAuteurView(mainPane);
        });

        miDeleteBoekenkast = new MenuItem("Boekenkast");
        miDeleteBoekenkast.setOnAction(event -> {
            mainPane.getChildren().clear();
            new DeleteBoekenkastView(mainPane);
        });
        
        miDeleteActeur = new MenuItem("Acteur");
        miDeleteActeur.setOnAction(event -> {
            mainPane.getChildren().clear();
            new DeleteActeurView(mainPane);
        });
        
        miDeleteAuteur = new MenuItem("Auteur");
        miDeleteAuteur.setOnAction(event -> {
            mainPane.getChildren().clear();
            new DeleteAuteurView(mainPane);
        });
        
        miDeleteBibliotheek = new MenuItem("Bibliotheek");
        miDeleteBibliotheek.setOnAction(event -> {
            mainPane.getChildren().clear();
            new DeleteBibliotheekView(mainPane);
        });
        
        miDeleteFilmrek = new MenuItem("Filmrek");
        miDeleteFilmrek.setOnAction(event -> {
            mainPane.getChildren().clear();
            new DeleteFilmRekView(mainPane);
        });
        
        miDeleteFilm = new MenuItem("Film");
        miDeleteFilm.setOnAction(event -> {
            mainPane.getChildren().clear();
            new DeleteFilmView(mainPane);
        });
        
        miDeleteBoek = new MenuItem("Boek");
        miDeleteBoek.setOnAction(event -> {
            mainPane.getChildren().clear();
            new DeleteBoekView(mainPane);
        });

        //voeg item toe aan menu
        mToevoegen.getItems().addAll(miInsertBibliotheek, miInsertBoek, miInsertFilm, miInsertActeur, miInsertAuteur, miInsertBoekenkast, miInsertFilmrek);
        mAanpassen.getItems().addAll(miUpdateBibliotheek, miUpdateBoek, miUpdateFilm, miUpdateActeur, miUpdateAuteur);
        mVerwijderen.getItems().addAll(miDeleteBibliotheek, miDeleteBoek, miDeleteFilm, miDeleteActeur, miDeleteAuteur, miDeleteFilmrek, miDeleteBoekenkast);

        //voeg menu toe aan balk
        getMenus().addAll(mToevoegen);
        getMenus().addAll(mAanpassen);
        getMenus().addAll(mVerwijderen);

    }
}
