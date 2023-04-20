import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTagger;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.SimpleTokenizer;
import org.jsoup.Jsoup;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class kinoBot extends TelegramLongPollingBot {
    @Override
    public String getBotToken() {
        String token = "";
        try (InputStream input = Files.newInputStream(Paths.get("src/main/resources/telegram.properties"))) {
            Properties properties = new Properties();
            properties.load(input);
            token = properties.getProperty("botToken");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return token;
    }

//    public void onUpdateReceived(Update update) {
//        if (update.hasMessage()) {
//            Message message = update.getMessage();
//
//            if (message.hasText()) {
//
//
//                SendMessage sendMessageRequest = new SendMessage();
//                sendMessageRequest.setChatId(message.getChatId()
//                        .toString());
//                sendMessageRequest.enableMarkdown(true);
//
//                ArrayList<String> nouns = new ArrayList<String>();
//
//                // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//                InputStream modelIn = null;
//                try {
//                    modelIn = new FileInputStream("src/models/sent_model.bin");
//                } catch (FileNotFoundException e) {
//                    throw new RuntimeException(e);
//                }
//                POSModel model = null;
//                try {
//                    model = new POSModel(modelIn);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                POSTaggerME tagger = new POSTaggerME(model);
//
//                // токенизация строки
//                String sentence = message.getText();
//                SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
//                String[] tokens = tokenizer.tokenize(sentence);
//
//                // определение частей речи
//                String[] tags = tagger.tag(tokens);
//
//
//                for (int i = 0; i < tags.length; i++) {
//                    if (tags[i].contains("NNP") || tags[i].contains("NNS")
//                            || tags[i].contains("NN")
//                            || tags[i].contains("NNPS")
//                            || tags[i].contains("JJ")) {
//                        System.out.println(tokens[i] + " is a plural noun");
//                    }
//                    if (tags[i].contains("посоветуй") || tags[i].contains("фильм")
//                            || tags[i].contains("Посоветуй")
//                            || tags[i].contains("Фильм")
//                            || tags[i].contains("кино")
//                            || tags[i].contains("Кино")) {
//                    } else {
//                        nouns.add(tags[i]);
//                    }
//                }
//                if (nouns.size() == 0) {
//                    Document doc = null;
//                    try {
//                        doc = Jsoup.connect("https://www.kinopoisk.ru/lists/movies/popular-films/").get();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        sendMessageRequest.setText("Проблемы с подключением - пожалуйста проверьте подключение к интернету!");
//                    }
//
//                    Elements el = doc.getElementsByClass("desktop-list-main-info_secondaryTitle__ighTt");
//
//                    int i = 0;
//
//                    for (Element element : el) {
//
//                        if (element.select("a").attr("href") != null) {
//                            i++;
//
//                            sendMessageRequest.setText(element.select("[base-movie-main-info_link__YwtP1]").text()
//                                    + " - Чтобы посмотреть "
//                                    + "[Нажмите сюда]("
//                                    + element.select("a").attr("href") + ")");
//                            if (i > 5) {
//                                break;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }




    @Override
    public String getBotUsername() {
        return "aboutkino_bot";
    }

    @Override
    public void onUpdateReceived(Update update) {

    }

    static class  botConfig {
        public static void main(String[] args) throws TelegramApiException {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            try {
                telegramBotsApi.registerBot(new kinoBot());
            } catch (TelegramApiException e) {
                e.printStackTrace();

            }
        }

    }
}
