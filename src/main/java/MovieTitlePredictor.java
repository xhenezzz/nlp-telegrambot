import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MovieTitlePredictor {
    public String predictMovieTitle(String description) throws IOException {
        TokenizerModel model = new TokenizerModel(new FileInputStream("en-token.bin"));
        Tokenizer tokenizer = new TokenizerME(model);
        String[] tokens = tokenizer.tokenize(description);

// загрузка модели
        InputStream modelIn = new FileInputStream("path/to/model.bin");
        TokenNameFinderModel tokenNameFinderModel = new TokenNameFinderModel(modelIn);
        modelIn.close();

// создание экземпляра NameFinderME
        NameFinderME nameFinder = new NameFinderME(tokenNameFinderModel);

// предсказание названия фильма
        Span[] nameSpans = nameFinder.find(tokens);
        String[] names = Span.spansToStrings(nameSpans, tokens);
        String movieTitle = names[0]; // первый элемент в массиве names будет содержать предсказанное название фильма

// использование предсказанного названия фильма
        System.out.println("Предсказанное название фильма: " + movieTitle);

        return null;
    }
}
