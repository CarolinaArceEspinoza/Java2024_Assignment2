package org.comicMovies.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.comicMovies.app.HelloApplication;

import java.io.File;
import java.io.IOException;
import java.net.URL;



public class homePageController {
    @FXML
    private ImageView imageUniverse1;

    @FXML
    private ImageView imageUniverse2;

    @FXML
    private RadioButton mcu_option;

    @FXML
    private RadioButton dcu_option;

    public final String URI = "src/main/resources/org/comicMovies/app/view/MoviesList.fxml";

    public void mcuButton(ActionEvent actionEvent) throws IOException {
        switchSC(URI, actionEvent);
    }

    public void dcuButton(ActionEvent actionEvent) throws IOException {
        switchSC(URI, actionEvent);
    }

    public void switchSC(String fxmlFileName, ActionEvent actionEvent) throws IOException {

       URL url = new File(fxmlFileName).toURI().toURL();

        MoviesListController controller = new MoviesListController();
        controller.setUniverse(mcu_option.isSelected());
        FXMLLoader loader = new FXMLLoader(url);
        loader.setController(controller);

        Parent root = loader.load();

        Scene scene = new Scene(root,1300, 700);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }
}