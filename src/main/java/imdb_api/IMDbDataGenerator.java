package imdb_api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class IMDbDataGenerator {

    public static void main(String[] args) {
        try {
            // Выполняем запрос к IMDb API и получаем данные о фильмах
            String json = IMDbApiClient.sendRequest("MetacriticReviews", "/tt1375666");

            // Обрабатываем полученные данные и сохраняем в текстовый файл
            FileWriter writer = new FileWriter("src/main/imdb_corpus.txt");
            // Обработка JSON и извлечение нужных данных из ответа IMDb API
            // В данном примере предполагается, что IMDb API возвращает JSON-объект вида {"items": [...]}, где items - массив фильмов
            // В реальном проекте необходимо произвести анализ структуры ответа IMDb API и извлечение нужных данных в соответствии с вашими требованиями
            JSONObject jsonObject = new JSONObject(json);
            JSONArray moviesArray = jsonObject.getJSONArray("items");
            for (int i = 0; i < moviesArray.length(); i++) {
                JSONObject movie = moviesArray.getJSONObject(i);
                String author = movie.getString("author");
                String content = movie.getString("content");
                String rate = movie.getString("rate");
                // В данном примере извлекаем данные о заголовке, сюжете и жанре фильма
                // Можете дополнить этот код или изменить его в соответствии с вашими требованиями
                // Записываем данные в текстовый файл
                writer.write("Автор: " + author + "\n");
                writer.write("Рецензия: " + content + "\n");
                writer.write("Оценка: " + rate + "\n");
                writer.write("------------------------------" + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
