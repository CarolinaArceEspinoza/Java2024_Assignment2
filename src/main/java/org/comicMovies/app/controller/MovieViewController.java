package org.comicMovies.app.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.comicMovies.app.apiClient.SimpleApiHttpClient;
import org.comicMovies.app.model.DetailMovie;
import org.comicMovies.app.model.Movies;
import org.comicMovies.app.model.RespMovies;

import java.net.URL;
import java.util.ResourceBundle;

/* CONTROLLER CLASS TO MANAGES THE FUNCTIONALITY OF MovieView.fxml */
public class MovieViewController implements Initializable {

    private int idCurrentMovie;
    private int pageBack;
    private SimpleApiHttpClient apiClient;
    private DetailMovie detailMovie;
    /* INITIALIZES THE CONTROLLER, SETS UP RADIO BUTTON GROUP, LOAD INITIAL DATA AND SETS DEFAULT SELECTION */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        apiClient = new SimpleApiHttpClient();
        connApi();

    }

    private void connApi(){
        String url = "https://api.themoviedb.org/3/movie/" + idCurrentMovie + "?language=en-US";

        String responseData = apiClient.fetchData(url);
        detailMovie = apiClient.parseJsonDetail(responseData);
        System.out.println(detailMovie);
    }

    public void setConfigure(int IDMovie, int pageSource) {
        idCurrentMovie = IDMovie;
        pageBack = pageSource;
    }

    /* LOADS CHART DATA FROM THE DATABASE BASED ON THE DATA SELECTOR */




    @FXML
    void handleRadioButtonAction(ActionEvent event) {
        // Action is handled dynamically by the change listener attached to the toggle group
    }


}