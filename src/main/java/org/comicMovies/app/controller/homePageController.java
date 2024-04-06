package org.comicMovies.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class homePageController {
    @FXML
    private RadioButton mcu_option;

    @FXML
    private RadioButton dcu_option;

    // URI of the FXML file for the MoviesList
    public final String URI = "src/main/resources/org/comicMovies/app/view/MoviesList.fxml";

    // Method to handle MCU button action
    public void mcuButton(ActionEvent actionEvent) throws IOException {
        switchSC(URI, actionEvent);
    }

    // Method to handle DCU button action
    public void dcuButton(ActionEvent actionEvent) throws IOException {
        switchSC(URI, actionEvent);
    }

    // Method to switch scenes
    public void switchSC(String fxmlFileName, ActionEvent actionEvent) throws IOException {

        // Convert the FXML file path to URL
        URL url = new File(fxmlFileName).toURI().toURL();

        // Create a MoviesListController instance and set the universe option
        MoviesListController controller = new MoviesListController();
        controller.setUniverse(mcu_option.isSelected());

        // Load the FXML file using FXMLLoader and set the controller
        FXMLLoader loader = new FXMLLoader(url);
        loader.setController(controller);

        // Load the root node from the FXML file
        Parent root = loader.load();

        // Create a new scene with the root node and set its dimensions
        Scene scene = new Scene(root,1300, 700);

        // Get the current stage and set the scene
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}