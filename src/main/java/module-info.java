module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.net.http;
    requires com.google.gson;


    opens org.comicMovies.app to javafx.fxml;
    exports org.comicMovies.app;
    exports org.comicMovies.app.model;
    opens org.comicMovies.app.model to javafx.fxml;
    exports org.comicMovies.app.controller;
    opens org.comicMovies.app.controller to javafx.fxml;
    exports org.comicMovies.app.apiClient;
    opens org.comicMovies.app.apiClient to javafx.fxml;
}