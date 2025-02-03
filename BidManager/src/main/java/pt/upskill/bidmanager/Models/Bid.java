package pt.upskill.bidmanager.Models;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;
import java.util.List;


@Entity
public class Bid {
    @Id
    private int id;

    @NotNull
    private float bid;

    @NotNull
    private Date bidDate;


    // Relação muitos-para-um com User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Relação muitos-para-um com AuctionItem
    @ManyToOne
    @JoinColumn(name = "auction_item_id", nullable = false)
    private AuctionItem auctionItem;


}
