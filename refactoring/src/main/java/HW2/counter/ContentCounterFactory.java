package HW2.counter;

import HW2.service.ContentServiceFactory;

public class ContentCounterFactory {
    public static ContentCounter getVkContentCounter() {
        return new ContentCounter(ContentServiceFactory.getVkService());
    }
}
