import java.util.*;
import java.util.stream.Collectors;

public class QueryEngine {

    private IndexedMatrix matrix;

    public QueryEngine(IndexedMatrix matrix) {
        this.matrix = matrix;
    }

    public List<Integer> intersect(String key1, String key2) {
        List<Integer> l1 = matrix.getValues(key1.toLowerCase());
        List<Integer> l2 = matrix.getValues(key2.toLowerCase());
        return intersect(l1, l2);
    }

    public List<Integer> intersect(List<Integer> l1, String key2) {
        List<Integer> l2 = matrix.getValues(key2.toLowerCase());
        return intersect(l1, l2);
    }

    public List<Integer> intersect(String key1, List<Integer> l2) {
        List<Integer> l1 = matrix.getValues(key1.toLowerCase());
        return intersect(l1, l2);
    }

    public List<Integer> intersect(List<Integer> l1, List<Integer> l2) {
        List<Integer> answer = new ArrayList<>();
        if (l1 == null || l2 == null || l1.isEmpty() || l2.isEmpty()) {
            return answer;
        }
        l1.add(-1);
        l2.add(-1);
        Iterator<Integer> p1 = l1.iterator();
        Iterator<Integer> p2 = l2.iterator();

        int p1Value = p1.next();
        int p2Value = p2.next();
        do  {
            if (p1Value == p2Value) {
                answer.add(p1Value);
                p1Value = p1.next();
                p2Value = p2.next();
            } else {
                if (p1Value < p2Value) {
                    p1Value = p1.next();
                } else {
                    p2Value = p2.next();
                }
            }
        }
        while (p1.hasNext() && p2.hasNext());
        return answer;
    }

    public List<Integer> or(List<Integer> l1, String key2) {
        List<Integer> l2 = matrix.getValues(key2.toLowerCase());
        return or(l1, l2);
    }

    public List<Integer> or(String key1, List<Integer> l2) {
        List<Integer> l1 = matrix.getValues(key1.toLowerCase());
        return or(l1, l2);
    }

    public List<Integer> or(String key1, String key2) {
        List<Integer> l1 = matrix.getValues(key1.toLowerCase());
        List<Integer> l2 = matrix.getValues(key2.toLowerCase());
        return or(l1, l2);
    }

    public List<Integer> or(List<Integer> l1, List<Integer> l2) {
        List<Integer> answer = new ArrayList<>();
        TreeSet<Integer> treeSet = new TreeSet<>();
        treeSet.addAll(l1);
        treeSet.addAll(l2);
        answer.addAll(treeSet);
        return answer;
    }

    public List<Integer> not(String key1) {
        List<Integer> l1 = matrix.getValues(key1.toLowerCase());
        return not(l1);
    }

    public List<Integer> not(List<Integer> l1) {
        List<Integer> answer = new ArrayList<>();
        List<Integer> l2 = matrix.getAllTexts();
        answer.addAll(l2.stream().filter(integer -> !l1.contains(integer)).collect(Collectors.toList()));
        return answer;
    }

}
