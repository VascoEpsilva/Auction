package pt.upskill.bidmanager.DTO;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import pt.upskill.bidmanager.Models.Bid;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuctionItemDTOCreate {

    private int itemId;

    private Date endDate;

}
