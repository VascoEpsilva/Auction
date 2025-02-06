package pt.upskill.bidmanager.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.upskill.bidmanager.DTO.AuctionItemDTO;
import pt.upskill.bidmanager.DTO.AuctionItemDTOCreate;
import pt.upskill.bidmanager.DTO.BidDTO;
import pt.upskill.bidmanager.Models.AuctionItem;
import pt.upskill.bidmanager.Services.AuctionItemService;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auctions")
public class AuctionItemController {

    private final AuctionItemService auctionItemService;

    @Autowired
    public AuctionItemController(AuctionItemService auctionItemService) {
        this.auctionItemService = auctionItemService;
    }

    /**
     * POST /api/v1/auctions
     * Creates a new AuctionItem (reactive).
     */
    @PostMapping
    public Mono<ResponseEntity<AuctionItemDTO>> createAuctionItem(@RequestBody AuctionItemDTOCreate createDTO) {
        return auctionItemService.createAuctionItem(createDTO)
                .map(createdDto -> new ResponseEntity<>(createdDto, HttpStatus.CREATED))
                .onErrorResume(ex -> {
                    return Mono.just(ResponseEntity.badRequest().build());
                });
    }

    /**
     * GET /api/v1/auctions/{id}
     * Retrieves a single AuctionItemDTO by ID (blocking).
     */
    @GetMapping("/{id}")
    public ResponseEntity<AuctionItemDTO> getAuctionItem(@PathVariable int id) {
        try {
            AuctionItemDTO dto = auctionItemService.getAuctionItemById(id);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (RuntimeException ex) {
            // Auction not found or other error
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET /api/v1/auctions
     * Lists all auctions (blocking).
     * The service calls finalizeExpiredAuctions() first.
     */
    @GetMapping
    public ResponseEntity<List<AuctionItemDTO>> getAllAuctions() {
        List<AuctionItemDTO> list = auctionItemService.getAllAuctions();
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * GET /api/v1/auctions/active
     * Lists all active auctions (blocking).
     * The service calls finalizeExpiredAuctions() first.
     */
    @GetMapping("/active")
    public ResponseEntity<List<AuctionItemDTO>> getAllActiveAuctions() {
        List<AuctionItemDTO> list = auctionItemService.getAllActiveAuctions();
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * GET /api/v1/auctions/inactive
     * Lists all inactive auctions (blocking).
     * The service calls finalizeExpiredAuctions() first.
     */
    @GetMapping("/inactive")
    public ResponseEntity<List<AuctionItemDTO>> getAllInactiveAuctions() {
        List<AuctionItemDTO> list = auctionItemService.getAllInactiveAuctions();
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("{id}/bids")
    public ResponseEntity<List<BidDTO>> getAllBids(@RequestParam int id) {
        List<BidDTO> list = auctionItemService.getAllBidsByAuctionId(id);
        return list.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(list, HttpStatus.OK);
    }
}
