package pt.upskill.bidmanager.WebClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pt.upskill.bidmanager.DTO.CategoryDTO;
import pt.upskill.bidmanager.DTO.ItemDTO;
import pt.upskill.bidmanager.DTO.SaleDTO;
import pt.upskill.bidmanager.DTO.SaleDTOCreate;
import reactor.core.publisher.Mono;

@Service
public class AuctionAPIClient {
    private final WebClient webClient;

    @Autowired
    public AuctionAPIClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<ItemDTO> getItemById(int id) {
        return webClient.get()
                .uri("/Items/{id}", id)
                .retrieve()
                .bodyToMono(ItemDTO.class);
    }

    public Mono<CategoryDTO> getCategoryById(int id) {
        return webClient.get()
                .uri("/Category/{id}", id)
                .retrieve()
                .bodyToMono(CategoryDTO.class);
    }

    public Mono<SaleDTO> getSaleById(int id) {
        return webClient.get()
                .uri("/Sales/{id}", id)
                .retrieve().bodyToMono(SaleDTO.class);
    }

    public Mono<SaleDTO> createSale(SaleDTOCreate saleDTOCreate) {
        return webClient.post()
                .uri("/Sales")
                .bodyValue(saleDTOCreate)
                .retrieve()
                .bodyToMono(SaleDTO.class);
    }
}