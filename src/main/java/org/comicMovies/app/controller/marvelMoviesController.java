package org.comicMovies.app.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import org.comicMovies.app.model.Movies;
import org.comicMovies.app.model.SimpleApiHttpClient;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class marvelMoviesController extends BasedController {

    @FXML
    private TableView<Movies> containerTable;

    @FXML
    private TableColumn<Movies, Integer> idColumn;

    @FXML
    private TableColumn<Movies, String> titleColumn;

    @FXML
    private TableColumn<Movies, String> dateColumn;

    private ToggleGroup toggleGroup;

    @FXML
    private RadioButton mcu_option;

    @FXML
    private RadioButton dceu_option;

    private ObservableList<Movies> selectedMovies;

    private SimpleApiHttpClient apiClient;

    public void initialize(URL location, ResourceBundle resources) {
        toggleGroup = new ToggleGroup();
        mcu_option.setToggleGroup(toggleGroup);
        dceu_option.setToggleGroup(toggleGroup);

        apiClient = new SimpleApiHttpClient();

        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                RadioButton selectedRadioButton = (RadioButton) newValue;

                if (selectedRadioButton.equals(mcu_option)) {
                    selectedMovies = loadMovies("mcu");
                } else if (selectedRadioButton.equals(dceu_option)) {
                    selectedMovies = loadMovies("dceu");
                }

                updateTable(selectedMovies);
            }
        });

        mcu_option.setSelected(Boolean.TRUE);

        // Llamar a la API después de la inicialización
        callApi();
    }


    public void callApi() {
        if (toggleGroup != null) {
            RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
            if (selectedRadioButton != null) {
                if (selectedRadioButton.equals(mcu_option)) {
                    selectedMovies = loadMovies("mcu");
                } else if (selectedRadioButton.equals(dceu_option)) {
                    selectedMovies = loadMovies("dceu");
                }

                updateTable(selectedMovies);
            }
        }
    }

    private ObservableList<Movies> loadMovies(String with_keywords) {
        String url = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=true&language=en-US&page=1&sort_by=popularity.desc&with_keywords=1805547";

        try {
            String responseData = apiClient.fetchData(url);
            List<Movies> movies = apiClient.parseJson(responseData);
            return FXCollections.observableArrayList(movies);
        } catch (Exception e) {
            System.err.println("Error loading movies: " + e.getMessage());
            return null;
        }
    }

    private void updateTable(ObservableList<Movies> movies) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("release_date"));
        containerTable.setItems(movies);
    }
}
