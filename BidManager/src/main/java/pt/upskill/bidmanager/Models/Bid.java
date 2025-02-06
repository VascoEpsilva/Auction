package pt.upskill.bidmanager.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
