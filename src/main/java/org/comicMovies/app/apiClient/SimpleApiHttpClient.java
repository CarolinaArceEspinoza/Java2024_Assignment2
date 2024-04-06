package org.comicMovies.app.apiClient;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.comicMovies.app.model.DetailMovie;
import org.comicMovies.app.model.Movies;
import org.comicMovies.app.model.RespMovies;

import java.lang.reflect.Type;

public class SimpleApiHttpClient {

    public String fetchData(String url) {
        try {
            // Create a new HttpClient instance
            HttpClient client = HttpClient.newHttpClient();

            // Build a GET request with the specified URI, headers, and method
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url)) // Set the request URI
                    .GET()
                    .header("accept", "application/json")
                    .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjMWU3ZmJiYjQwNmQ5ODc3MzYyZDA4ZDFjZWQzZWYyMiIsInN1YiI6IjY1ZjljZWUxMWNmZTNhMDEyZmY3MTQ3OSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.JoTwl2xI8JoY1J52c4jdtHE6ZRAJsEwVCu_TsiWpMdo")
                    .build();

            // Send the request and obtain the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Print the API response to the console
            System.out.println("API Response: " + response.body());

            // Check if the response status code indicates success
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return response.body(); // Return the response body as a String
            } else {
                // Throw an IOException if the response status code indicates an error
                throw new IOException("- - - - HTTP error code: " + response.statusCode() + "- - - -");
            }
        } catch (IOException | InterruptedException | URISyntaxException e) {
            // Handle exceptions gracefully and print error message
            System.err.println("- - - - Error fetching data: " + e.getMessage() + " - - - -");
            return null; // Return null to indicate failure
        }
    }

    public RespMovies parseJson(String json) {
        try {
            // Create a Gson instance with desired configurations
            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                    .setFieldNamingStrategy(FieldNamingPolicy.IDENTITY)
                    .setPrettyPrinting()
                    .serializeNulls()
                    .setLenient()
                    .setVersion(1.0)
                    .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                    .create();

            // Parse the JSON string into RespMovies object using Gson
            RespMovies preRes = gson.fromJson(json, RespMovies.class);

            return preRes; // Return the parsed RespMovies object
        } catch (Exception e) {
            // Handle exceptions gracefully and print error message
            System.err.println("Error parsing JSON: " + e.getMessage());
            return null; // Return null to indicate failure
        }
    }

    public DetailMovie parseJsonDetail(String json) {
        try {
            // Create a Gson instance with desired configurations
            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                    .setFieldNamingStrategy(FieldNamingPolicy.IDENTITY)
                    .setPrettyPrinting()
                    .serializeNulls()
                    .setLenient()
                    .setVersion(1.0)
                    .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                    .create();

            // Parse the JSON string into DetailMovie object using Gson
            DetailMovie preRes = gson.fromJson(json, DetailMovie.class);

            return preRes; // Return the parsed DetailMovie object
        } catch (Exception e) {
            // Handle exceptions gracefully and print error message
            System.err.println("Error parsing JSON: " + e.getMessage());
            return null; // Return null to indicate failure
        }
    }
}