package model_train;

import java.io.*;
import java.nio.charset.StandardCharsets;

import opennlp.tools.doccat.*;
import opennlp.tools.util.*;

public class MovieClassifierTrainer {
    public static void main(String[] args) throws IOException {
        try {
        //Путь к файлу с обучающими данными
        String[] trainingDataFile = new String[]{"src/main/training_data.txt"};

        // Загрузка обучающих данных
        InputStreamFactory inputStreamFactory = new MarkableFileInputStreamFactory(new File("src/main/training_data.txt"));
        ObjectStream<String> lineStream = new PlainTextByLineStream(inputStreamFactory, StandardCharsets.UTF_8);
        ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);

        // конфигурация для модели
        TrainingParameters params = new TrainingParameters();
        params.put(TrainingParameters.ITERATIONS_PARAM, 10+"");
        params.put(TrainingParameters.CUTOFF_PARAM, 0+"");

        // Создание объекта тренера
        DoccatModel model = DocumentCategorizerME.train("ru", sampleStream, params, new DoccatFactory());
        System.out.println("\nМодель успешно обучена..");

        // Сохранение модели в файл
        FileOutputStream fileOutputStream = new FileOutputStream(new File("src/models/model.bin"));
        model.serialize(fileOutputStream);

        // тестируем файл модели, подвергая его предсказанию
        DocumentCategorizer doccat = new DocumentCategorizerME(model);
//        String[] docWords = "История двух молодых людей, которые принадлежат к разным мирам. Баби ― богатая девушка, которая отображает доброту и невинность. Аче — мятежный мальчик, импульсивный, бессознательный, склонный к риску и опасности. Это маловероятно, практически невозможно, но их встреча неизбежна, и в этом неистовом путешествии между ними возникает первая большая любовь".replaceAll("[^А-яа-я]", " ").split(" ");
//            String[] docWords = "Американское семейство отправляется из Чикаго в Европу, но в спешке сборов бестолковые родители забывают дома... одного из своих детей. Юное создание, однако, не теряется и демонстрирует чудеса изобретательности. И когда в дом залезают грабители, им приходится не раз пожалеть о встрече с милым крошкой.".replaceAll("[^А-яа-я]", " ").split(" ");
//            String[] docWords = "Подросток Марти с помощью машины времени, сооружённой его другом-профессором доком Брауном, попадает из 80-х в далекие 50-е. Там он встречается со своими будущими родителями, ещё подростками, и другом-профессором, совсем молодым.".replaceAll("[^А-яа-я]", " ").split(" ");
            String[] docWords = "В южном городке орудует шайка валютчиков, возглавляемая Шефом и его помощником Графом (в быту — Геной Козодоевым). Скромный советский служащий и примерный семьянин Семен Семенович Горбунков отправляется в зарубежный круиз на теплоходе, где также плывет Граф, который должен забрать бриллианты в одном из восточных городов и провезти их в загипсованной руке. Но из-за недоразумения вместо жулика на условленном месте падает ничего не подозревающий Семен Семенович, и драгоценный гипс накладывают ему." .replaceAll("[^А-яа-я]", " ").split(" ");

            double[] aProbs = doccat.categorize(docWords);

        // вывести вероятности категорий
        System.out.println("\n---------------------------------\nКатегория : Вероятность\n---------------------------------");
        for(int i=0;i<doccat.getNumberOfCategories();i++){
            System.out.println(doccat.getCategory(i)+" : "+aProbs[i]);
        }
        System.out.println("---------------------------------");

        System.out.println("\n"+doccat.getBestCategory(aProbs)+" : является прогнозируемой категорией для данного предложения.\n");
    }
            catch (IOException e) {
            System.out.println("Исключение при чтении обучающего файла. Пожалуйста, проверьте.");
            e.printStackTrace();
        }
    }
}
