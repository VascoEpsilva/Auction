package pt.upskill.bidmanager.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BidDTOCreate {
    private float bid;
    private Date bidDate;
    private int clientid;
    private int auctionid;
}
