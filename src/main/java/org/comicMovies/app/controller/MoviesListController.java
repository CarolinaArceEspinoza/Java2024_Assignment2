package org.comicMovies.app.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.comicMovies.app.model.Movies;
import org.comicMovies.app.apiClient.SimpleApiHttpClient;
import org.comicMovies.app.model.RespMovies;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MoviesListController implements Initializable {

    @FXML
    private TableView<Movies> containerTable;

    @FXML
    private TableColumn<Movies, Integer> idColumn;

    @FXML
    private TableColumn<Movies, String> titleColumn;

    @FXML
    private TableColumn<Movies, String> dateColumn;

    @FXML
    private ToggleGroup universeToggleGroup;

    @FXML
    private RadioButton mcu_option;

    @FXML
    private RadioButton dcu_option;

    @FXML
    private Pagination pager;

    private SimpleApiHttpClient apiClient;
    private boolean marvelOn;

    private String keyword;

    private final String KEY_MCU = "180547";

    private final String KEY_DCU = "229266";

    private final String URI = "src/main/resources/org/comicMovies/app/view/MovieView.fxml";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Initialize API client
        apiClient = new SimpleApiHttpClient();

        // Listen for selection changes in the table
        containerTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showMovieDetails(newValue.getId());
            }
        });

        // Set initial universe selection and load movies
        if (marvelOn) {
            keyword = KEY_MCU;
            mcu_option.setSelected(true);
            dcu_option.setSelected(false);
        } else {
            keyword = KEY_DCU;
            mcu_option.setSelected(false);
            dcu_option.setSelected(true);
        }
        loadMovies(keyword, 1);

        // Assign radio button events
        mcu_option.setToggleGroup(universeToggleGroup);
        dcu_option.setToggleGroup(universeToggleGroup);

        // Handle radio button selection change
        mcu_option.setOnAction(event -> {
            keyword = KEY_MCU;
            loadMovies(keyword, 1);
            dcu_option.setSelected(false);
            mcu_option.setSelected(true);
        });

        dcu_option.setOnAction(event -> {
            keyword = KEY_DCU;
            loadMovies(keyword, 1);
            mcu_option.setSelected(false);
            dcu_option.setSelected(true);
        });

        // Set pagination page factory
        pager.setPageFactory((pageIndex) -> createPage(containerTable.getItems(), pageIndex));
    }

    // Method to load movies from API
    private void loadMovies(String keyword, int page) {
        try {
            RespMovies resM = connApi(keyword, page);
            pager.setPageCount(resM.getTotal_pages());
            pager.setMaxPageIndicatorCount(resM.getTotal_pages());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading movies: " + e.getMessage());
        }
    }

    // Method to connect to API and retrieve movie data
    private RespMovies connApi(String keyword, int page){
        String url = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=" + page + "&sort_by=popularity.desc&with_keywords=" + keyword;

        String responseData = apiClient.fetchData(url);
        RespMovies resM = apiClient.parseJson(responseData);

        if (resM != null) {
            ObservableList<Movies> observableMovies = FXCollections.observableArrayList(resM.getResults());
            updateTable(observableMovies);
        }

        return resM;
    }

    // Method to update table with movie data
    private void updateTable(ObservableList<Movies> movies) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("release_date"));
        containerTable.setItems(movies);
    }

    // Method to create a page in the table pagination
    public TableView<Movies> createPage(ObservableList<Movies> movies, int page) {
        connApi(keyword, page + 1);
        containerTable.setMinSize(400.0, 500.0);
        containerTable.setMaxHeight(400.0);
        return containerTable;
    }

    // Method to set the universe (MCU or DCU)
    public void setUniverse(boolean mcuButton) {
        marvelOn = mcuButton;
    }

    // Method to show movie details in a separate stage
    private void showMovieDetails(int movieId) {

        try {
            URL url = new File(URI).toURI().toURL();

            MovieViewController controller = new MovieViewController();
            controller.setConfigure(movieId, 0);
            FXMLLoader loader = new FXMLLoader(url);
            loader.setController(controller);

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}