package pt.upskill.bidmanager.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.upskill.bidmanager.DTO.BidDTO;
import pt.upskill.bidmanager.DTO.BidDTOCreate;
import pt.upskill.bidmanager.DTO.ItemDTO;
import pt.upskill.bidmanager.Mappers.BidMapper;
import pt.upskill.bidmanager.Models.AuctionItem;
import pt.upskill.bidmanager.Models.Bid;
import pt.upskill.bidmanager.Models.Client;
import pt.upskill.bidmanager.Services.Repositories.AuctionItemRepository;
import pt.upskill.bidmanager.Services.Repositories.BidRepository;
import pt.upskill.bidmanager.Services.Repositories.ClientRepository;
import pt.upskill.bidmanager.WebClient.AuctionAPIClient;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BidService {

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private AuctionItemRepository auctionItemRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AuctionAPIClient auctionAPIClient;

    /**
     * Creates a new bid for an AuctionItem, enforcing rules:
     *  1) AuctionItem must be active.
     *  2) If no bids exist, the new bid >= initialPrice.
     *  3) If bids exist, new bid > the highest existing bid.
     */
    public BidDTO createBid(BidDTOCreate createDTO) {
        AuctionItem auctionItem = auctionItemRepository.findById(createDTO.getAuctionid())
                .orElseThrow(() -> new RuntimeException("AuctionItem not found with ID: " + createDTO.getAuctionid()));

        if (!auctionItem.isActive()) {
            throw new RuntimeException("Cannot place bid on an inactive auction.");
        }

        // 3) Determine the "current" highest or baseline price
        List<Bid> existingBids = auctionItem.getBids(); // might be empty
        float currentHighestBid;
        if (existingBids == null || existingBids.isEmpty()) {
            // no bids -> must be >= initial price
            currentHighestBid = auctionItem.getInitialPrice();
            if (createDTO.getBid() < currentHighestBid) {
                throw new RuntimeException("First bid cannot be less than the initial price (" + currentHighestBid + ")");
            }
        } else {
            // some bids exist -> new bid must exceed the highest
            currentHighestBid = existingBids.stream()
                    .map(Bid::getBid)
                    .max(Float::compare)
                    .orElse(auctionItem.getInitialPrice());

            if (createDTO.getBid() <= currentHighestBid) {
                throw new RuntimeException("Your bid must exceed the current highest bid (" + currentHighestBid + ")");
            }
        }

        // 4) Fetch the Client
        Client client = clientRepository.findById(createDTO.getClientid())
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + createDTO.getClientid()));

        // 5) Build and save the new Bid
        Bid newBid = BidMapper.toModel(createDTO, client, auctionItem);
        // If you want to let the DB generate 'id', ensure it's annotated with @GeneratedValue
        // or handle it manually
        Bid saved = bidRepository.save(newBid);

        // 6) Return a BidDTO
        return BidMapper.toDTO(saved, auctionAPIClient);
    }

    /**
     * (Optional) Retrieve a single bid by ID
     */
    public BidDTO getBidById(int id) {
        Bid bid = bidRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bid not found with ID: " + id));

        return toDTO(bid);
    }

    public BidDTO getHighestBidByAuctionId(int auctionId) {
        // 1) Fetch the AuctionItem
        AuctionItem auctionItem = auctionItemRepository.findById(auctionId)
                .orElseThrow(() -> new RuntimeException("Auction not found with ID: " + auctionId));


        // 2) Check if there are any bids
        List<Bid> bids = auctionItem.getBids();
        if (bids == null || bids.isEmpty()) {
            throw new RuntimeException("No bids found for auction ID: " + auctionId);
        }

        // 3) Get the last bid
        Bid highestBid = bids.getLast();


        // 4) Convert that Bid to a BidDTO
        //    You already have a private method or a mapper to do so. For example:
        return toDTO(highestBid);
    }

    /**
     * (Optional) List all bids, returning a list of BidDTO
     */
    public List<BidDTO> getAllBids() {
        List<Bid> bids = bidRepository.findAll();
        return bids.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private BidDTO toDTO(Bid bid) {
        return BidMapper.toDTO(bid, auctionAPIClient);
    }



}
