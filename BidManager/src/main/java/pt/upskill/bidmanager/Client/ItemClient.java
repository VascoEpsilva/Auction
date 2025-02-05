package pt.upskill.bidmanager.Client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ItemClient {

    private final WebClient webClient;

    public ItemClient(WebClient webClient) {
        this.webClient = webClient;
    }


}
