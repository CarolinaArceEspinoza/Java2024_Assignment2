package org.comicMovies.app.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;

public class Movies {

    /* VARIABLES */
    private Integer id;
    private Integer with_keywords;
    private String title;
    private String overview;
    private String poster_path;
    private String release_date;

    /* CONSTRUCTOR NO ARGUMENTS */
    public Movies() {
    }

    /* CONSTRUCTOR WITH ARGUMENTS */
    public Movies(Integer id, Integer universeKeyword, String title, String overview, String poster_path, String release_date) {
        this.id = id;
        this.with_keywords = with_keywords;
        this.title = title;
        this.overview = overview;
        this.poster_path = poster_path;
        this.release_date = release_date;
    }

    /* SETTERS AND GETTERS */

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUniverseKeyword() {
        return with_keywords;
    }

    public void setUniverseKeyword(Integer universeKeyword) {
        this.with_keywords = universeKeyword;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }


}