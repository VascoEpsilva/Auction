package pt.upskill.bidmanager.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.upskill.bidmanager.DTO.*;
import pt.upskill.bidmanager.Mappers.AuctionItemMapper;
import pt.upskill.bidmanager.Mappers.BidMapper;
import pt.upskill.bidmanager.Models.AuctionItem;
import pt.upskill.bidmanager.Models.Bid;
import pt.upskill.bidmanager.Services.Repositories.AuctionItemRepository;
import pt.upskill.bidmanager.WebClient.AuctionAPIClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AuctionItemService {

    @Autowired
    private AuctionItemRepository auctionItemRepository;

    @Autowired
    private AuctionAPIClient auctionAPIClient;

    public Mono<AuctionItemDTO> createAuctionItem(AuctionItemDTOCreate createDTO) {
        return auctionAPIClient.getItemById(createDTO.getItemId())
                .map(itemDTO -> {
                    if (!itemDTO.getIsAvailable()) {
                        throw new RuntimeException("Item is not available, cannot create AuctionItem.");
                    }
                    AuctionItem auctionItem = AuctionItemMapper.toModel(createDTO, itemDTO.getPrice());
                    AuctionItem saved = auctionItemRepository.save(auctionItem);
                    return AuctionItemMapper.toDTO(saved, itemDTO.getName(), 0.0f);
                });
    }

    public AuctionItemDTO getAuctionItemById(int auctionId) {
        AuctionItem auctionItem = auctionItemRepository.findById(auctionId)
                .orElseThrow(() -> new RuntimeException("Auction not found with ID: " + auctionId));

        checkIfExpired(auctionItem);

        ItemDTO itemDTO = auctionAPIClient.getItemById(auctionItem.getItemId()).block();
        String itemName = (itemDTO != null) ? itemDTO.getName() : "Unknown item";

        float lastBidPrice = auctionItem.getBids().stream()
                .map(Bid::getBid)
                .max(Float::compare)
                .orElse(0.0f);

        return AuctionItemMapper.toDTO(auctionItem, itemName, lastBidPrice);
    }


    public List<AuctionItemDTO> getAllAuctions() {
        finalizeExpiredAuctions();
        List<AuctionItem> auctions = auctionItemRepository.findAll();
        return getAuctionItemDTOS(auctions);
    }

    public List<AuctionItemDTO> getAllInactiveAuctions() {
        finalizeExpiredAuctions();
        //finds the inactive auctions
        List<AuctionItem> auctions = auctionItemRepository.findByActiveFalse();
        return getAuctionItemDTOS(auctions);


    }

    public List<AuctionItemDTO> getAllActiveAuctions() {
        finalizeExpiredAuctions();
        List<AuctionItem> auctions = auctionItemRepository.findByActiveTrue();
        return getAuctionItemDTOS(auctions);
    }

    public List<BidDTO> getAllBidsByAuctionId(int auctionId) {
        AuctionItem auctionItem = auctionItemRepository.findById(auctionId)
                .orElseThrow(() -> new RuntimeException("Auction not found with ID: " + auctionId));
        List<BidDTO> bidDTOS = new ArrayList<>();
        for(Bid bid : auctionItem.getBids()) {
            bidDTOS.add(toBidDTO(bid));
        }
        return bidDTOS;
    }



    public void finalizeExpiredAuctions() {
        List<AuctionItem> activeAuctions = auctionItemRepository.findByActiveTrue();
        for (AuctionItem auction : activeAuctions) {
            checkIfExpired(auction);
            }
    }

    private void checkIfExpired(AuctionItem auction) {
        Date now = new Date();

        if (!auction.getEndDate().after(now)) {
            // If it has bids, create a Sale
            if (auction.getBids() != null && !auction.getBids().isEmpty()) {
                // Find the highest bid
                float lastBidPrice = auction.getBids().stream()
                        .map(Bid::getBid)
                        .max(Float::compare)
                        .orElse(0.0f);

                // Create a Sale entity
                SaleDTOCreate sale = new SaleDTOCreate();
                sale.setSaleDate(new Date()); // or now
                sale.setSalePrice(lastBidPrice);
                sale.setItemId(auction.getItemId());

                // Save the sale
                auctionAPIClient.createSale(sale).block();
            }

            // Deactivate the auction
            auction.setActive(false);
            auctionItemRepository.save(auction);
        }
    }

    private List<AuctionItemDTO> getAuctionItemDTOS(List<AuctionItem> auctions) {
        List<AuctionItemDTO> dtoList = new ArrayList<>();

        for (AuctionItem auctionItem : auctions) {
            ItemDTO itemDTO = auctionAPIClient.getItemById(auctionItem.getItemId()).block();
            String itemName = (itemDTO != null) ? itemDTO.getName() : "Unknown item";

            float lastBidPrice = auctionItem.getBids().stream()
                    .map(Bid::getBid)
                    .max(Float::compare)
                    .orElse(0.0f);

            AuctionItemDTO dto = AuctionItemMapper.toDTO(auctionItem, itemName, lastBidPrice);
            dtoList.add(dto);
        }
        return dtoList;
    }


    private BidDTO toBidDTO(Bid bid) {
        return BidMapper.toDTO(bid, auctionAPIClient);
    }
}


