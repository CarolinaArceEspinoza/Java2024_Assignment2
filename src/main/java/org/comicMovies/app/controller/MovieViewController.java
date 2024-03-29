package org.comicMovies.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/* CONTROLLER CLASS TO MANAGES THE FUNCTIONALITY OF MovieView.fxml */
public class MovieViewController extends BasedController implements Initializable {


    /* INITIALIZES THE CONTROLLER, SETS UP RADIO BUTTON GROUP, LOAD INITIAL DATA AND SETS DEFAULT SELECTION */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /* LOADS CHART DATA FROM THE DATABASE BASED ON THE DATA SELECTOR */




    @FXML
    void handleRadioButtonAction(ActionEvent event) {
        // Action is handled dynamically by the change listener attached to the toggle group
    }


}