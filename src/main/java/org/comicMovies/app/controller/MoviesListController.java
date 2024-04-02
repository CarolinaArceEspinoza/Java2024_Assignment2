package org.comicMovies.app.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.comicMovies.app.model.Movies;
import org.comicMovies.app.apiClient.SimpleApiHttpClient;
import org.comicMovies.app.model.RespMovies;

import java.net.URL;
import java.util.ResourceBundle;

public class MoviesListController extends BasedController implements Initializable {

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
    private RadioButton dceu_option;

    private SimpleApiHttpClient apiClient;

    private boolean marvelOn;

    private boolean dcOn;

    @FXML
    private Pagination pager;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        apiClient = new SimpleApiHttpClient();
        loadMovies(mcu_option.isSelected() ? "180547" : "229266"); // Cargar películas de Marvel directamente al iniciar la vista

        mcu_option.setSelected(marvelOn);
        dceu_option.setSelected(dcOn);

        // Asignar eventos a los botones de radio
        mcu_option.setToggleGroup(universeToggleGroup);
        dceu_option.setToggleGroup(universeToggleGroup);

        // Manejar el cambio de selección de los botones de radio
        mcu_option.setOnAction(event -> {
            loadMovies("180547");
            dceu_option.setSelected(false);
        });

        dceu_option.setOnAction(event -> {
            loadMovies("229266");
            mcu_option.setSelected(false);
        });


    }

    private void loadMovies(String keyword) {

        String url = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc&with_keywords=" + keyword;

        try {
            String responseData = apiClient.fetchData(url);
            RespMovies resM = apiClient.parseJson(responseData);
            if (resM != null) {
                ObservableList<Movies> observableMovies = FXCollections.observableArrayList(resM.getResults());
                updateTable(observableMovies);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading movies: " + e.getMessage());
        }
    }

    private void updateTable(ObservableList<Movies> movies) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("release_date"));
        containerTable.setItems(movies);

        pager = new Pagination((movies.size() / 1000 + 1), 0);
        pager.setPageCount(movies.size());
        pager.setPageFactory((Integer pageIndex) -> createPage(movies, pageIndex));
        //paginator.setCurrentPageIndex(3);
        //paginator.setMaxPageIndicatorCount(3);
    }

    public void setUniverse(boolean mcuButton, boolean dceuButton) {


        marvelOn = mcuButton;
        dcOn = dceuButton;


    }



    public TableView<Movies> createPage(ObservableList<Movies> movies, int page) {
        containerTable.getItems().setAll(movies.get(page));
        return containerTable;
    }
}
