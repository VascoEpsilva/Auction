package pt.upskill.bidmanager.Services.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.upskill.bidmanager.Models.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
