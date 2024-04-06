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
        // Load the FXML file for the home page
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("homePage.fxml"));

        // Create a scene with the loaded FXML file and set its dimensions
        Scene scene = new Scene(fxmlLoader.load(), 1300, 700);

        // Load an icon image for the stage
        Image image = new Image("file:src/main/resources/org/comicMovies/app/images/icon.png");
        stage.getIcons().add(image);

        // Set the title of the stage
        stage.setTitle("THE BEST CINEMATOGRAPHIC SUPERHEROES UNIVERSE");

        // Set the scene to the stage and display it
        stage.setScene(scene);
        stage.show();

        // Call the API after showing the window
        homePageController controller = fxmlLoader.getController();
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch();
    }
}