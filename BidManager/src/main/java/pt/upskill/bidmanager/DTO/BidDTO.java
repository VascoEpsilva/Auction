package pt.upskill.bidmanager.DTO;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.hateoas.RepresentationModel;
import pt.upskill.bidmanager.Models.AuctionItem;
import pt.upskill.bidmanager.Models.Client;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BidDTO extends RepresentationModel<BidDTO> {

    private int id;

    private float bid;

    private Date bidDate;

    private String clientName;

    private String ItemName;
}
