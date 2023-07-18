import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    // Helper method to URL encode a given value using UTF-8 charset
    public static String encodeURL(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        String apikey = System.getenv("API_KEY");
        String query;

        // Initialize Gson for JSON parsing
        Gson gson = new Gson();
        String jsonRequest;

        // Create a Scanner to read user input
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter a search query
        System.out.println("Enter query: ");
        query = encodeURL(scanner.nextLine());

        // Create an HTTP request to fetch TV series data from the RapidAPI service
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://imdb8.p.rapidapi.com/auto-complete?q=" + query))
                .header("X-RapidAPI-Key", apikey)
                .header("X-RapidAPI-Host", "imdb8.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response;
        try {
            // Send the HTTP request and obtain the response
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            // Handle any exceptions that might occur during the HTTP request
            throw new RuntimeException(e);
        }

        // Store the JSON response in a string
        jsonRequest = response.body();

        // Deserialize the JSON response into a TVSeriesResponse object using Gson
        TVSeriesResponse tvSeriesResponse = gson.fromJson(jsonRequest, TVSeriesResponse.class);

        // Check if the response contains TV series data and print the results
        if (tvSeriesResponse != null && tvSeriesResponse.getSeries() != null && tvSeriesResponse.getSeries().length != 0) {
            TVSeries[] sortedSeries = tvSeriesResponse.getSeries();
            Arrays.sort(sortedSeries, new TVSeriesRankComparator());

            int count = 0;
            for (TVSeries tvSeries : sortedSeries) {
                // Exclude any TV series with the ID "/imdbpicks"
                if (!tvSeries.getId().equals("/imdbpicks")) {
                    count++;

                    // Print the TV series details: name, cast, and year
                    System.out.println(count + ". " + tvSeries.getName());
                    System.out.println("Rank: " + tvSeries.getRank());
                    System.out.println("Cast: " + tvSeries.getCast());
                    System.out.println("Year: " + tvSeries.getYear());
                    System.out.println("\n");
                }
            }
        }
    }
}
