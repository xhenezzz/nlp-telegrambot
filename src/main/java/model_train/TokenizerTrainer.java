package model_train;

import opennlp.tools.dictionary.Dictionary;
import opennlp.tools.tokenize.*;
import opennlp.tools.util.*;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class TokenizerTrainer {

    public static void main(String[] args) throws IOException  {
        InputStreamFactory dataIn = new MarkableFileInputStreamFactory(new File("src/main/output.txt"));
        Charset charset = Charset.forName("UTF-8");
        ObjectStream<String> lineStream = new PlainTextByLineStream(dataIn, charset);

        // Создание объекта TokenSampleStream на основе разметки
        ObjectStream<TokenSample> sampleStream = new TokenSampleStream(lineStream);

        // Настройка параметров модели
        TrainingParameters params = new TrainingParameters();
        params.put(TrainingParameters.ITERATIONS_PARAM, 100);
        params.put(TrainingParameters.CUTOFF_PARAM, 5);
        params.put(TrainingParameters.TRAINER_TYPE_PARAM, 1);

        // Обучение модели
        TokenizerModel model = TokenizerME.train(sampleStream, new TokenizerFactory("ru", null, true, null), params);

        // Сохранение модели
        OutputStream modelOut = new BufferedOutputStream(new FileOutputStream("src/models/token.bin"));
        model.serialize(modelOut);
        // Тестирование модели
        TokenizerME tokenizer = new TokenizerME(model);
        String sentence = "Привет, мир! Это тестовое предложение.";
        String[] tokens = tokenizer.tokenize(sentence);
        for (String token : tokens) {
            System.out.println(token);
        }
    }
}
