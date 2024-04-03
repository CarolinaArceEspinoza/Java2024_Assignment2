package org.comicMovies.app.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.comicMovies.app.model.Movies;
import org.comicMovies.app.apiClient.SimpleApiHttpClient;
import org.comicMovies.app.model.RespMovies;

import javax.security.auth.callback.Callback;
import java.net.URL;
import java.util.ResourceBundle;

public class MoviesListController implements Initializable {

    @FXML
    private Text textoPrueba;

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

    private SimpleApiHttpClient apiClient;

    private boolean marvelOn;


    @FXML
    private Pagination pager;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        apiClient = new SimpleApiHttpClient();
        String keyword;

        if(marvelOn){
            keyword = "180547";
            mcu_option.setSelected(Boolean.TRUE);
            dcu_option.setSelected(Boolean.FALSE);
        }else{
            keyword = "229266";
            mcu_option.setSelected(Boolean.FALSE);
            dcu_option.setSelected(Boolean.TRUE);
        }

        loadMovies(keyword, 1);

        // Asignar eventos a los botones de radio
        mcu_option.setToggleGroup(universeToggleGroup);
        dcu_option.setToggleGroup(universeToggleGroup);

        // Manejar el cambio de selección de los botones de radio
        mcu_option.setOnAction(event -> {
            loadMovies("180547", 1);
            dcu_option.setSelected(false);
        });

        dcu_option.setOnAction(event -> {
            loadMovies("229266", 1);
            mcu_option.setSelected(false);
        });
        pager.setPageFactory((pageIndex) -> {
            //LLAMAR connApi(key, pageIndex+1)
            RespMovies m = connApi(keyword, pageIndex +1);


            return null;
        });


        pager.setOnMouseClicked(event -> {
            Integer currentNumb = pager.getCurrentPageIndex();
            Integer nextNumb = currentNumb + 1;

            System.out.println(pager.getCurrentPageIndex());
           loadMovies(mcu_option.isSelected() ? "180547" : "229266", nextNumb);


                    textoPrueba.setText(String.valueOf(nextNumb));

        });

        RespMovies respMovies = new RespMovies();
        Movies movies = new Movies();
    }

    private void loadMovies(String keyword, int page) {
        try {
            RespMovies resM = connApi(keyword,page);
            //resM.getTotal_pages();
            pager.setPageCount(resM.getTotal_pages());
            pager.setMaxPageIndicatorCount(resM.getTotal_pages());
            //Pagination pager = new Pagination();
            //pager.setPageCount(resM.getTotal_pages());

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading movies: " + e.getMessage());
        }
    }

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

    private void updateTable(ObservableList<Movies> movies) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("release_date"));
        containerTable.setItems(movies);



        //pager = new Pagination(4);
        //pager = new Pagination((movies.size() / 1000 + 1), 0);
        //pager.setPageCount(movies.size());
        //pager.setPageFactory((Integer pageIndex) -> createPage(movies, pageIndex));
        //paginator.setCurrentPageIndex(3);
        //paginator.setMaxPageIndicatorCount(3);

    }

    public void setUniverse(boolean mcuButton) {
        marvelOn = mcuButton;
    }

    public TableView<Movies> createPage(ObservableList<Movies> movies, int page) {
        containerTable.getItems().setAll(movies.get(page));
        return containerTable;
    }
}
