import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class QueryEngine {

    private IndexedMatrix matrix;

    public QueryEngine(IndexedMatrix matrix) {
        this.matrix = matrix;
    }

    public List<Integer> intersect(String key1, String key2) {
        List<Integer> answer = new ArrayList<>();
        List<Integer> l1 = matrix.getValues(key1.toLowerCase());
        List<Integer> l2 = matrix.getValues(key2.toLowerCase());
        if (l1 == null || l2 == null) {
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

}
