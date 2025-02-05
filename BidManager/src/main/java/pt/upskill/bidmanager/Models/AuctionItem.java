package pt.upskill.bidmanager.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AuctionItem {
    @Id
    private int id;

    @NotNull
    private int itemId;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

    @NotNull
    private float initialPrice;

    @OneToMany(mappedBy = "auctionItem")
    private List<Bid> bids;
}
