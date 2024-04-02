package org.comicMovies.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class BasedController {

    public final String HOME_PATH = "src/main/resources/org/comicMovies/app/homePage.fxml";
    public final String MOVIE_PATH = "src/main/resources/org/comicMovies/app/view/MovieView.fxml";
    public final String MCU_ID = "src/main/resources/org/comicMovies/app/view/MoviesList.fxml";
    public final String DCEU_ID = "src/main/resources/org/comicMovies/app/view/MoviesList.fxml";
        //"229266"



    /* SWITCHES TO A DIFFERENT SCENE BASED ON THE FXML FILE NAME */
    public void switchSC(String fxmlFileName, ActionEvent actionEvent) throws IOException {
        URL url = new File(fxmlFileName).toURI().toURL();
        Parent root = FXMLLoader.load(url);

        Scene scene = new Scene(root,1300, 700);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /* NAVIGATION BAR OPTIONS */
    public void homeViewButton(ActionEvent actionEvent) throws IOException {
        // Switch to 'homePage.fxml'
        switchSC(HOME_PATH, actionEvent);
    }

    public void mcuButton(ActionEvent actionEvent) throws IOException {
        switchSC(MCU_ID, actionEvent);
    }

    public void dceuButton(ActionEvent actionEvent) throws IOException {
    switchSC(DCEU_ID, actionEvent);
    }

    public void movieViewButton(ActionEvent actionEvent) throws IOException {
        // Switch to 'MovieView.fxml'
        switchSC(MOVIE_PATH, actionEvent);
    }

}
