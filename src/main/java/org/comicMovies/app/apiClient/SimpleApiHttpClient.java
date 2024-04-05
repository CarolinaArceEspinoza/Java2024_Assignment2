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
            HttpClient client = HttpClient.newHttpClient(); // Correct way to create HttpClient

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url)) // Use uri() instead of url() to set the request URI
                    .GET()
                    .header("accept", "application/json")
                    .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjMWU3ZmJiYjQwNmQ5ODc3MzYyZDA4ZDFjZWQzZWYyMiIsInN1YiI6IjY1ZjljZWUxMWNmZTNhMDEyZmY3MTQ3OSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.JoTwl2xI8JoY1J52c4jdtHE6ZRAJsEwVCu_TsiWpMdo")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            /* Imprimir la respuesta de la API en la consola */
            System.out.println("Respuesta de la API: " + response.body());

            /* Check if the response status is successful */
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return response.body(); // The response body should be returned as a String
            } else {
                throw new IOException("- - - - HTTP error code: " + response.statusCode() + "- - - -");
            }
        } catch (IOException | InterruptedException | URISyntaxException e) {
            /* Handle exceptions gracefully */
            System.err.println("- - - - Error fetching data: " + e.getMessage() + " - - - -");
            return null; // Return null to indicate failure
        }
    }

    public RespMovies parseJson(String json) {
        try {
            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                    .setFieldNamingStrategy(FieldNamingPolicy.IDENTITY)
                    .setPrettyPrinting()
                    .serializeNulls()
                    .setLenient()
                    .setVersion(1.0)
                    .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                    .create();  // Create a Gson instance

            RespMovies preRes = gson.fromJson(json, RespMovies.class);

            return preRes; // Convert the JSON string into a list of Post objects using Gson
        } catch (Exception e) {
            /* Handle exceptions gracefully */
            System.err.println("Error parsing JSON: " + e.getMessage()); // Print a user-friendly error message
            return null; // Return null to indicate failure
        }
    }

    public DetailMovie parseJsonDetail(String json) {
        try {
            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                    .setFieldNamingStrategy(FieldNamingPolicy.IDENTITY)
                    .setPrettyPrinting()
                    .serializeNulls()
                    .setLenient()
                    .setVersion(1.0)
                    .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                    .create();  // Create a Gson instance

            DetailMovie preRes = gson.fromJson(json, DetailMovie.class);

            return preRes; // Convert the JSON string into a list of Post objects using Gson
        } catch (Exception e) {
            /* Handle exceptions gracefully */
            System.err.println("Error parsing JSON: " + e.getMessage()); // Print a user-friendly error message
            return null; // Return null to indicate failure
        }
    }
}
