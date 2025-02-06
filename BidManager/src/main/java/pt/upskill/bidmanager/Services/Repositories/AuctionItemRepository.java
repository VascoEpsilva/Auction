package pt.upskill.bidmanager.Services.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.upskill.bidmanager.Models.AuctionItem;

import java.util.List;

public interface AuctionItemRepository extends JpaRepository<AuctionItem, Integer> {

    List<AuctionItem> findByActiveTrue();
    List<AuctionItem> findByActiveFalse();
}
