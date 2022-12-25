package HW2.service;

import HW2.ContentConverter;
import HW2.ContentPiece;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.newsfeed.responses.SearchResponse;
import org.jetbrains.annotations.NotNull;

import java.net.URISyntaxException;
import java.util.List;

public class VkService implements ContentService {
    private final VkApiClient vkClient;
    private final ServiceActor serviceActor;

    public VkService(VkApiClient vkApiClient, ServiceActor serviceActor) {
        this.vkClient = vkApiClient;
        this.serviceActor = serviceActor;
    }

    @NotNull
    public List<ContentPiece> searchContent(
        String hashtag,
        Long startTime,
        Long endTime
    ) {
        try {
            SearchResponse response = this.vkClient.newsfeed()
                .search(this.serviceActor)
                .q("#" + hashtag)
                .startTime(startTime.intValue())
                .endTime(endTime.intValue())
                .execute();

            System.out.println(
                response.getItems().stream().map(ContentConverter::vkPostToContentPiece).toList()
            );

            return response.getItems().stream()
                .map(ContentConverter::vkPostToContentPiece)
                .toList();
        } catch (ApiException | ClientException e) {
            throw new RuntimeException(e);
        }
    }
}
