package HW2.service;

import HW2.ContentPiece;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ContentService {
    @NotNull
    List<ContentPiece> searchContent(String hashtag, Long startTime, Long endTime);
}
