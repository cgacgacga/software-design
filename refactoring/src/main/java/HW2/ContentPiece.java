package HW2;

public class ContentPiece {
    private final Integer date;
    private final String text;

    public ContentPiece(String content, Integer date) {
        this.text = content;
        this.date = date;
    }

    public Integer getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public String toString() {
        return date.toString() + " " + text;
    }
}
