import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void randomTest() {
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();
        StringBuilder message = new StringBuilder("\n");
        for (int i = 0; i < 1000; ++i) {
            double numberBetweenZeroAndOne = StdRandom.uniform();
            if (ads.size() > 0) {
                if (numberBetweenZeroAndOne < 0.25) {
                    sad.addFirst(i);
                    ads.addFirst(i);
                    message.append("addFirst(" + i + ")\n");
                } else if (numberBetweenZeroAndOne < 0.5) {
                    sad.addLast(i);
                    ads.addLast(i);
                    message.append("addLast(" + i + ")\n");
                } else if (numberBetweenZeroAndOne < 0.75) {
                    Integer x = sad.removeFirst();
                    Integer y = ads.removeFirst();
                    message.append("removeFirst()\n");
                    assertEquals(message.toString(), x, y);
                } else {
                    Integer x = sad.removeLast();
                    Integer y = ads.removeLast();
                    message.append("removeLast()\n");
                    assertEquals(message.toString(), x, y);
                }
            } else {
                if (numberBetweenZeroAndOne < 0.5) {
                    sad.addFirst(i);
                    ads.addFirst(i);
                    message.append("addFirst(" + i + ")\n");
                } else {
                    sad.addLast(i);
                    ads.addLast(i);
                    message.append("addLast(" + i + ")\n");
                }
            }
        }
    }
}
