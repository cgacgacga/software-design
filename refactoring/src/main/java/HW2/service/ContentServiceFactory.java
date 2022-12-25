package HW2.service;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;

public class ContentServiceFactory {
    private static final Integer vkAppId = 51439078;
    private static final String vkAccessToken
        = "385f70a0385f70a0385f70a0e03b4f95463385f385f70a05b67f715ee2d4451ccbbf45c";

    public static VkService getVkService() {
        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vkClient = new VkApiClient(transportClient);

        ServiceActor serviceActor = new ServiceActor(vkAppId, vkAccessToken);

        return new VkService(vkClient, serviceActor);
    }
}
