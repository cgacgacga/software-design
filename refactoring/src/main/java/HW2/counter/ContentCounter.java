package HW2.counter;

import HW2.ContentPiece;
import HW2.service.ContentService;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ContentCounter {
    private final ContentService contentService;

    public ContentCounter(ContentService contentService) {
        this.contentService = contentService;
    }

    @Nullable
    public List<Long> getPostCountByHour(String hashtag, int hours, long untilTimestamp) {
        assert hours >= 1 && hours <= 24 :
                "Неподдерживаемое количество часов. Возможные значения: от 1 до 24";

        long SECONDS_IN_HOUR = 3600;
        long sinceTimestamp = untilTimestamp - hours * SECONDS_IN_HOUR;
        var contentPieces = getContentPieces(
                hashtag,
                sinceTimestamp,
                untilTimestamp
        );

        if (contentPieces == null) {
            return null;
        }

        List<Long> result = new ArrayList<>();

        for (int i = 0; i < hours; i++) {
            long startTime = sinceTimestamp + i * SECONDS_IN_HOUR;
            long endTime = startTime + SECONDS_IN_HOUR;

            long count = contentPieces.stream()
                .filter(p -> p.getDate() >= startTime && p.getDate() < endTime)
                .count();
            result.add(count);
        }

        return result;
    }

    private List<ContentPiece> getContentPieces(
            String hashtag,
            Long startTime,
            Long endTime
    ) {
        return this.contentService.searchContent(hashtag, startTime, endTime);
    }
}
