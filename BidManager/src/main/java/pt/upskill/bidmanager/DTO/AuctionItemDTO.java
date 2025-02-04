package pt.upskill.bidmanager.DTO;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.hateoas.RepresentationModel;
import pt.upskill.bidmanager.Models.Bid;

import java.sql.Driver;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuctionItemDTO extends RepresentationModel<AuctionItemDTO> {
    private int id;
    private String itemName;
    private Date endDate;
    private float initialPrice;
    private float lastBidPrice;
}
