package model_train;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.sentdetect.SentenceSampleStream;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

/**
 * @author tutorialkart
 */
public class SentenceDetectorTrainer {

    public static void main(String[] args) {
        try {
            new SentenceDetectorTrainer().trainSentDectectModel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void trainSentDectectModel() throws IOException {

        // Путь к файлу с обучающими данными
        InputStreamFactory in = new MarkableFileInputStreamFactory(new File("src/main/imdb_corpus.txt"));

        // Параметры, используемые алгоритмом машинного обучения Maxent для обучения своих весов
        TrainingParameters mlParams = new TrainingParameters();
        mlParams.put(TrainingParameters.ITERATIONS_PARAM, Integer.toString(15));
        mlParams.put(TrainingParameters.CUTOFF_PARAM, Integer.toString(1));

        // Обучить модель
        SentenceModel sentdetectModel = SentenceDetectorME.train(
                "ru",
                new SentenceSampleStream(new PlainTextByLineStream(in, StandardCharsets.UTF_8)),
                true,
                null,
                mlParams);

        // Сохраняем модель
        File outFile = new File("src/models/sent_model.bin");
        FileOutputStream outFileStream = new FileOutputStream(outFile);
        sentdetectModel.serialize(outFileStream);

        // Загружаем модель
        SentenceDetectorME sentDetector = new SentenceDetectorME(sentdetectModel);

        // Тестирование модели, а именно обнаружение предложений в тестовой строке
        String testString = ("Рецензия: Летом римейков, перезагрузок и сиквелов приходит «Начало». Пожалуй, самая оригинальная идея фильма за всю историю. Я непременно схожу на него снова!");
        System.out.println("\nТестовая строка: "+ testString);
        String[] sents = sentDetector.sentDetect(testString);
        System.out.println("---------Предложения, обнаруженные классом ME SentenceDetector с использованием сгенерированной модели-------");
        for(int i=0;i<sents.length;i++){
            System.out.println("Предложение:  "+(i+1)+" : "+sents[i]);
        }
    }
}