package pt.upskill.bidmanager.Services.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.upskill.bidmanager.Models.AuctionItem;

public interface AuctionItemRepository extends JpaRepository<AuctionItem, Integer> {
}
