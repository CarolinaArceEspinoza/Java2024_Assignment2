package org.comicMovies.app.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.comicMovies.app.apiClient.SimpleApiHttpClient;
import org.comicMovies.app.model.DetailMovie;


import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

/* CONTROLLER CLASS TO MANAGES THE FUNCTIONALITY OF MovieView.fxml */
public class MovieViewController implements Initializable {

    @FXML
    private Text txtId;
    @FXML
    private Text txtOverview;
    @FXML
    private Text txtDate;

    @FXML
    private Text txtTitle;

    private int idCurrentMovie;
    private int pageBack;
    private SimpleApiHttpClient apiClient;
    private DetailMovie detailMovie;
    @FXML
    private ImageView imgPoster;
    @FXML
    private VBox powerViewContainter;

    private final String URI_BACK = "https://image.tmdb.org/t/p/w1920_and_h800_multi_faces";

    private final String URI_IMG = "https://image.tmdb.org/t/p/w600_and_h900_bestv2";

    /* INITIALIZES THE CONTROLLER, SETS UP RADIO BUTTON GROUP, LOAD INITIAL DATA AND SETS DEFAULT SELECTION */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        apiClient = new SimpleApiHttpClient();
        connApi();
        updateInfo(detailMovie);

    }

    private void connApi(){
        String url = "https://api.themoviedb.org/3/movie/" + idCurrentMovie + "?language=en-US";
        String responseData = apiClient.fetchData(url);
        detailMovie = apiClient.parseJsonDetail(responseData);
    }

    public void setConfigure(int IDMovie, int pageSource) {
        idCurrentMovie = IDMovie;
        pageBack = pageSource;
    }

    private void updateInfo(DetailMovie movie) {
        txtTitle.setText(String.valueOf(movie.getOriginal_title()));
        txtOverview.setText(movie.getOverview());
        txtId.setText(String.valueOf(movie.getId()));

        txtDate.setText(movie.getRelease_date());
        imgPoster.setImage(new Image(URI_IMG + movie.getPoster_path()));
        powerViewContainter.setBackground(
                new Background(
                        Collections.singletonList(new BackgroundFill(
                                Color.BLACK,
                                new CornerRadii(500),
                                new Insets(10))),
                        Collections.singletonList(new BackgroundImage(
                                new Image(URI_BACK + movie.getBackdrop_path()),
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                BackgroundSize.DEFAULT)))
        );
    }


}