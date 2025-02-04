package pt.upskill.bidmanager.Models;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;


@Entity
public class Bid {
    @Id
    private int id;

    @NotNull
    private float bid;

    @NotNull
    private Date bidDate;


    // Relação muitos-para-um com Client
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    // Relação muitos-para-um com AuctionItem
    @ManyToOne
    @JoinColumn(name = "auction_item_id", nullable = false)
    private AuctionItem auctionItem;


}
