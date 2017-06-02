import java.util.*;

class IndexedMatrix {
    private Map<String, List<Integer>> dictionary;

    IndexedMatrix(List<List<String>> tokens) {
        dictionary = createMatrix(tokens);
    }

    private Map<String, List<Integer>> createMatrix(List<List<String>> tokens) {
        Map<String, List<Integer>> dictionary = new TreeMap<>();
        for (int i = 0; i < tokens.size(); i++) {
            for (int j = 0; j < tokens.get(i).size(); j++) {
                String token = tokens.get(i).get(j).toLowerCase();
                if (dictionary.containsKey(token)) {
                    if (!dictionary.get(token).contains(i + 1)) {
                        dictionary.get(token).add(i + 1);
                    }
                } else {
                    List<Integer> set = new ArrayList<>();
                    set.add(i + 1);
                    dictionary.put(token, set);
                }
            }
        }
        return dictionary;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Matrix : {\n");
        dictionary.forEach((s, integers) -> {
            builder.append("\t").append(s).append("=").append(integers).append("\n");
        });
        return builder.append("}").toString();
    }

    public List<Integer> getValues(String key) {
        return dictionary.get(key);
    }
}
