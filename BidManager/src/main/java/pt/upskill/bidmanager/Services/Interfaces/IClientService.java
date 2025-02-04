package pt.upskill.bidmanager.Services.Interfaces;

import org.springframework.data.domain.Page;
import pt.upskill.bidmanager.Models.Client;

import java.util.List;

public interface IClientService {
    public Client registerClient(Client client);
    public Page<Client> getAllClients(int page, int size);
    public Client getClientById(int id);
    public Client updateClient(Client client);
}
