package org.comicMovies.app.model;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class SimpleApiHttpClient {

    public String fetchData(String url) {
        try {
            /* Construct an HttpRequest object targeting the provided URL */
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .header("accept", "application/json")
                    .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjMWU3ZmJiYjQwNmQ5ODc3MzYyZDA4ZDFjZWQzZWYyMiIsInN1YiI6IjY1ZjljZWUxMWNmZTNhMDEyZmY3MTQ3OSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.JoTwl2xI8JoY1J52c4jdtHE6ZRAJsEwVCu_TsiWpMdo")
                    .build();

            /* Utilize HttpClient to send the HttpRequest and obtain the HttpResponse */
            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2) // Set HTTP version
                    .followRedirects(HttpClient.Redirect.NORMAL) // Follow redirects
                    .connectTimeout(Duration.ofSeconds(20)) // Set the timeout duration
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

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

    public List<Movies> parseJson(String json) {
        try {
            Gson gson = new Gson();  // Create a Gson instance
            Type postListType = new TypeToken<List<Movies>>() {}.getType(); // Define the type of the collection (List<Post>)

            return gson.fromJson(json, postListType); // Convert the JSON string into a list of Post objects using Gson
        } catch (Exception e) {
            /* Handle exceptions gracefully */
            System.err.println("Error parsing JSON: " + e.getMessage()); // Print a user-friendly error message
            return null; // Return null to indicate failure
        }
    }
}
