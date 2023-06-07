package com.tsapiszczak.gitapitask.githubapi;

import com.tsapiszczak.gitapitask.exception.APIException;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class GitApi {
    @Value("${github.api.url}")
    String connectionString;
    private static final String GET="GET";
    public JSONArray findReposByUser(String username) {

        try {
            URL url = new URL(connectionString +"users/"+ username + "/repos");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(GET);

            conn.setRequestProperty("Accept", "application/vnd.github.v3+json");

            if (conn.getResponseCode() == 404) {
                conn.disconnect();
                throw new APIException(404, "User not found.");
            }
            return getJsonArray(conn);
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private JSONArray getJsonArray(HttpURLConnection conn) throws IOException, JSONException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder responseBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            responseBuilder.append(line);
        }
        String response = responseBuilder.toString();
        JSONArray jsonResponse = new JSONArray(response);
        conn.disconnect();
        return jsonResponse;
    }

    @SneakyThrows
    public JSONArray findRepoBranchDetails(String username, String repositoryName) {
        URL url = new URL(connectionString +"repos/"+ username + "/" + repositoryName + "/branches");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(GET);

        conn.setRequestProperty("Accept", "application/vnd.github.v3+json");

        if (conn.getResponseCode() != 200) {
            conn.disconnect();
            throw new APIException(404, "Error during getting repository details! .");
        }
        return getJsonArray(conn);
    }
}
