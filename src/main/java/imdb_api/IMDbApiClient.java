package imdb_api;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IMDbApiClient {
    private static final String API_KEY = "k_tez9jwzz";
    private static final String BASE_URL = "https://imdb-api.com/en/API/";

    public static String sendRequest(String endpoint, String id) throws IOException {
        URL url = new URL("https://imdb-api.com/en/API/" + endpoint + "/k_tez9jwzz" + id); // замените на URL вашего API
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) { // Проверка на успешный ответ от сервера
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            String json = content.toString();
            return json;
        } else {
            System.out.println("HTTP request failed with response code: " + responseCode);
        }
        return null;
    }
//    public static void main(String[] args) {
//        try {
//            // Отправка HTTP-запроса
//            String endpoint = "MetacriticReviews"; // замените на свой эндпоинт
//            URL url = new URL("https://imdb-api.com/en/API/" + endpoint + "/k_tez9jwzz" + "/tt1375666"); // замените на URL вашего API
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            int responseCode = connection.getResponseCode();
//
//            if (responseCode == HttpURLConnection.HTTP_OK) { // Проверка на успешный ответ от сервера
//                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                String inputLine;
//                StringBuilder content = new StringBuilder();
//                while ((inputLine = in.readLine()) != null) {
//                    content.append(inputLine);
//                }
//                in.close();
//
//                String json = content.toString();
//                System.out.println("JSON response: " + json); // Вывод JSON-стринга на консоль
//            } else {
//                System.out.println("HTTP request failed with response code: " + responseCode);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
