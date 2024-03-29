package org.comicMovies.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.comicMovies.app.controller.homePageController;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("homePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1300, 700);
        Image image = new Image("file:src/main/resources/org/comicMovies/app/images/icon.png");
        stage.getIcons().add(image);
        stage.setTitle("Your Favorite Cinematic Heroes Universe");
        stage.setScene(scene);
        stage.show();

        // Llamar a la API después de mostrar la ventana
        homePageController controller = fxmlLoader.getController();
        controller.callApi();
    }

    public static void main(String[] args) {
        launch();
    }
}
