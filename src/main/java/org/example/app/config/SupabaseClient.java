package org.example.app.config;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class SupabaseClient {

    private static final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static String get(String endpoint) {
        try {
            String fullUrl = SupabaseConfig.URL + endpoint;
            System.out.println("GET: " + fullUrl);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(fullUrl))
                    .timeout(Duration.ofSeconds(10))
                    .header("apikey", SupabaseConfig.API_KEY)
                    .header("Authorization", "Bearer " + SupabaseConfig.API_KEY)
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            System.out.println("GET status: " + response.statusCode());
            System.out.println("GET body: " + response.body());
            return response.body();

        } catch (Exception e) {
            System.err.println("GET error: " + e.getMessage());
            e.printStackTrace();
            return "[]";
        }
    }

    public static int post(String endpoint, String json) {
        try {
            String fullUrl = SupabaseConfig.URL + endpoint;
            System.out.println("POST: " + fullUrl);
            System.out.println("POST body: " + json);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(fullUrl))
                    .timeout(Duration.ofSeconds(10))
                    .header("apikey", SupabaseConfig.API_KEY)
                    .header("Authorization", "Bearer " + SupabaseConfig.API_KEY)
                    .header("Content-Type", "application/json")
                    .header("Prefer", "return=minimal")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            System.out.println("POST status: " + response.statusCode());
            System.out.println("POST response: " + response.body());
            return response.statusCode();

        } catch (Exception e) {
            System.err.println("POST error: " + e.getMessage());
            e.printStackTrace();
            return 500;
        }
    }

    public static int patch(String endpoint, String json) {
        try {
            String fullUrl = SupabaseConfig.URL + endpoint;
            System.out.println("PATCH: " + fullUrl);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(fullUrl))
                    .timeout(Duration.ofSeconds(10))
                    .header("apikey", SupabaseConfig.API_KEY)
                    .header("Authorization", "Bearer " + SupabaseConfig.API_KEY)
                    .header("Content-Type", "application/json")
                    .header("Prefer", "return=minimal")
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            System.out.println("PATCH status: " + response.statusCode());
            System.out.println("PATCH response: " + response.body());
            return response.statusCode();

        } catch (Exception e) {
            System.err.println("PATCH error: " + e.getMessage());
            e.printStackTrace();
            return 500;
        }
    }
}