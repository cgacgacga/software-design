package HW2;

import HW2.counter.ContentCounter;
import HW2.counter.ContentCounterFactory;

import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        ContentCounter vkCounter = ContentCounterFactory.getVkContentCounter();
        var result = vkCounter.getPostCountByHour("инстаграм", 24, Instant.now().getEpochSecond());
        System.out.println(result);
    }
}
