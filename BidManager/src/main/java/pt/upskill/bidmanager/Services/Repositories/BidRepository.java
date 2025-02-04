package pt.upskill.bidmanager.Services.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.upskill.bidmanager.Models.Bid;

public interface BidRepository extends JpaRepository<Bid, Integer> {
}
