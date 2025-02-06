package pt.upskill.bidmanager.Mappers;

import pt.upskill.bidmanager.DTO.AuctionItemDTO;
import pt.upskill.bidmanager.DTO.AuctionItemDTOCreate;
import pt.upskill.bidmanager.DTO.ItemDTO;
import pt.upskill.bidmanager.Models.AuctionItem;

import java.util.ArrayList;
import java.util.List;

public class AuctionItemMapper {

    /**
     * Convert an AuctionItem entity to a DTO.
     * We assume you already know or have fetched:
     *  - itemName (from an external Item)
     *  - lastBidPrice (computed or from the highest bid)
     */
    public static AuctionItemDTO toDTO(AuctionItem auctionItem, String itemName, float lastBidPrice) {
        AuctionItemDTO dto = new AuctionItemDTO();
        dto.setId(auctionItem.getId());
        dto.setItemName(itemName);
        dto.setEndDate(auctionItem.getEndDate());
        dto.setActive(auctionItem.isActive());
        dto.setInitialPrice(auctionItem.getInitialPrice());
        dto.setLastBidPrice(lastBidPrice);
        return dto;
    }

    /**
     * Convert a DTOCreate into an AuctionItem entity.
     * We also accept an initialPrice (possibly from the external item).
     * The startDate might be "now" or set externally, depends on your logic.
     */
    public static AuctionItem toModel(AuctionItemDTOCreate dtoCreate, float initialPrice) {
        AuctionItem auctionItem = new AuctionItem();
        auctionItem.setItemId(dtoCreate.getItemId());
        auctionItem.setStartDate(new java.util.Date());
        auctionItem.setEndDate(dtoCreate.getEndDate());
        auctionItem.setActive(true);
        auctionItem.setInitialPrice(initialPrice);

        return auctionItem;
    }


}
