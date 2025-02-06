package pt.upskill.bidmanager.Mappers;

import org.springframework.beans.factory.annotation.Autowired;
import pt.upskill.bidmanager.DTO.BidDTO;
import pt.upskill.bidmanager.DTO.BidDTOCreate;
import pt.upskill.bidmanager.DTO.ItemDTO;
import pt.upskill.bidmanager.Models.AuctionItem;
import pt.upskill.bidmanager.Models.Bid;
import pt.upskill.bidmanager.Models.Client;
import pt.upskill.bidmanager.WebClient.AuctionAPIClient;

public class BidMapper {


    public static BidDTO toDTO(Bid bid, AuctionAPIClient auctionAPIClient) {
        BidDTO bidDTO = new BidDTO();
        ItemDTO itemDTO = auctionAPIClient.getItemById(bid.getAuctionItem().getItemId()).block();

        bidDTO.setId(bid.getId());
        bidDTO.setBid(bid.getBid());
        bidDTO.setBidDate(bid.getBidDate());
        bidDTO.setItemName( itemDTO == null ? "Unknown": itemDTO.getName());

        bidDTO.setClientName(bid.getClient().getName());

        return bidDTO;
    }

    public static Bid toModel(BidDTOCreate createDTO, Client client, AuctionItem auctionItem) {
        Bid bid = new Bid();
        // setId(...) if not auto-generated
        bid.setBid(createDTO.getBid());
        bid.setBidDate(createDTO.getBidDate());
        bid.setClient(client);
        bid.setAuctionItem(auctionItem);
        return bid;
    }
}