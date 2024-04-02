package org.comicMovies.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import org.comicMovies.app.HelloApplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class homePageController extends org.comicMovies.app.controller.BasedController {

    @FXML
    private RadioButton mcu_option;

    @FXML
    private RadioButton dceu_option;

    public void initialize() {

    }

    @FXML
      private void getSelection (ActionEvent event) {


        try {
            if (mcu_option.isSelected() || dceu_option.isSelected()) {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("MoviesList.fxml"));
                Parent root = loader.load();
                MoviesListController btnSelected = loader.getController();

                btnSelected.setUniverse(mcu_option.isSelected(), dceu_option.isSelected());

                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately (log or display error message)
        }









    }











}