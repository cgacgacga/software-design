package HW2.counter;

import HW2.ContentPiece;
import HW2.service.ContentService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ContentCounterTest {
    private ContentService contentService;
    private ContentCounter contentCounter;

    @Before
    public void setup() {
        contentService = mock(ContentService.class);
        contentCounter = new ContentCounter(contentService);
    }

    @Test
    public void simpleTest() {
        long since = 0;
        long until = 3600 * 3;
        when(contentService.searchContent("Россия", since, until))
            .thenReturn(
                List.of(
                    // 1-й час
                    new ContentPiece("", 0),
                    new ContentPiece("", 500),
                    new ContentPiece("", 1000),
                    // 2-й час
                    new ContentPiece("", 3600),
                    // 3-й час
                    new ContentPiece("", 3600 * 2),
                    new ContentPiece("", 3600 * 2 + 500)
                )
            );

        var result = contentCounter.getPostCountByHour("Россия", 3, until);
        assertThat(result).containsExactly(3L, 1L, 2L);
    }

    @Test
    public void hoursWithNoContentTest() {
        long since = 0;
        long until = 3600 * 4;
        when(contentService.searchContent("Россия", since, until))
            .thenReturn(
                List.of(
                    // 1-й час
                    new ContentPiece("", 0),
                    new ContentPiece("", 500),
                    new ContentPiece("", 1000),
                    // 2-й час: нет контента
                    // 3-й час
                    new ContentPiece("", 3600 * 2),
                    new ContentPiece("", 3600 * 2 + 500)
                    // 4-й час: нет контента
                )
            );

        var result = contentCounter.getPostCountByHour("Россия", 4, until);
        assertThat(result).containsExactly(3L, 0L, 2L, 0L);
    }
}
