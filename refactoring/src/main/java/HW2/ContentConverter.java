package HW2;

import com.vk.api.sdk.objects.wall.Wallpost;

public class ContentConverter {
    public static ContentPiece vkPostToContentPiece(Wallpost vkPost) {
        return new ContentPiece(vkPost.getText(), vkPost.getDate());
    }
}
