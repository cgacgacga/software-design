package HW2.service;

import HW2.ContentPiece;
import com.vk.api.sdk.actions.Newsfeed;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.newsfeed.responses.SearchResponse;
import com.vk.api.sdk.objects.wall.WallpostFull;
import com.vk.api.sdk.queries.newsfeed.NewsfeedSearchQuery;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VkServiceTest {
    private VkService vkService;

    @Before
    public void setup() throws ClientException, ApiException {
        VkApiClient vkClientMock = mock(VkApiClient.class);
        ServiceActor serviceActorMock = mock(ServiceActor.class);
        this.vkService = new VkService(vkClientMock, serviceActorMock);

        Newsfeed newsfeedMock = mock(Newsfeed.class);
        NewsfeedSearchQuery queryMock = mock(NewsfeedSearchQuery.class);

        List<WallpostFull> posts = List.of(
            getWallpost("post 1", 3500),
            getWallpost("post 2", 3700),
            getWallpost("post 3", 3800)
        );

        when(vkClientMock.newsfeed()).thenReturn(newsfeedMock);
        when(newsfeedMock.search(any(ServiceActor.class))).thenReturn(queryMock);
        when(queryMock.q(anyString())).thenReturn(queryMock);
        when(queryMock.startTime(anyInt())).thenReturn(queryMock);
        when(queryMock.endTime(anyInt())).thenReturn(queryMock);
        when(queryMock.execute()).thenReturn(new SearchResponse().setItems(posts));
    }

    @Test
    public void simpleTest() {
        var response = vkService.searchContent("hashtag", 2L, 3600L * 2);
        Assertions.assertThat(response)
            .usingRecursiveFieldByFieldElementComparator()
            .containsExactly(
                new ContentPiece("post 1", 3500),
                new ContentPiece("post 2", 3700),
                new ContentPiece("post 3", 3800)
            );
    }

    private WallpostFull getWallpost(String text, Integer date) {
        WallpostFull post = new WallpostFull();
        post.setText(text);
        post.setDate(date);

        return post;
    }
}
