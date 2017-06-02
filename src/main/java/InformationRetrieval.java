import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class InformationRetrieval {

    private List<String> stopWordList = Arrays.asList("the", "a", "to", "of", ",", ".",
            "'s", "?", ":", "-", "http://", "https://", "\"", "'", "`", "''");

    InformationRetrieval() {
    }

    public void compute() {
        List<String> articles = Arrays.asList("1.txt", "2.txt", "3.txt", "4.txt", "5.txt",
                "6.txt", "7.txt", "8.txt", "9.txt", "10.txt");
        List<List<String>> tokenList = parse(articles);
        //tokenList.forEach(System.out::println);
        IndexedMatrix matrix = new IndexedMatrix(tokenList);
        System.out.println(matrix);

        QueryEngine queryEngine = new QueryEngine(matrix);
        System.out.println("Cleese AND Pythons : " + queryEngine.intersect("Cleese", "Pythons"));
        System.out.println("Cleese AND Python : " + queryEngine.intersect("Cleese", "Python"));
        System.out.println("Brexit AND negative : " + queryEngine.intersect("Brexit", "negative"));
        System.out.println("UKIP AND market : " + queryEngine.intersect("UKIP", "market"));
    }

    private List<CoreLabel> tokenize(String path) {
        PTBTokenizer<CoreLabel> ptbt = null;
        try {
            ptbt = new PTBTokenizer<>(new FileReader("./src/main/resources/" + path),
                    new CoreLabelTokenFactory(), "");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert ptbt != null;
        return ptbt.tokenize();
    }

    private List<String> normalize(List<CoreLabel> tokenList) {
        List<String> result = new ArrayList<>();
        tokenList.forEach(token -> result.add(token.toString()));
        return result;
    }

    private List<String> stopWords(List<String> tokenList) {
        List<String> result = new ArrayList<>();
        tokenList.forEach(token -> {
            if (!stopWordList.contains(token)) {
                result.add(token);
            }
        });
        return result;
    }

    private List<List<String>> parse(List<String> paths) {
        List<List<String>> tokens = new ArrayList<>();
        for (String path : paths) {
            List<CoreLabel> tokenList = tokenize(path);
            List<String> normalList = normalize(tokenList);
            /* stemming */
            List<String> stopped = stopWords(normalList);
            tokens.add(stopped);
        }
        return tokens;
    }

}
