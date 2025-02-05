package pt.upskill.bidmanager.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pt.upskill.bidmanager.Models.Client;
import pt.upskill.bidmanager.Services.Interfaces.IClientService;
import pt.upskill.bidmanager.Services.Repositories.ClientRepository;

@Service
public class ClientService implements IClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client registerClient(Client client) {
        return this.clientRepository.save(client);
    }

    public Page<Client> getAllClients(int page, int size) {
        return this.clientRepository.findAll(PageRequest.of(page,size));
    }

    public Client getClientById(int id) {
        return this.clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + id));
    }

    public Client updateClient(Client client) {
        return this.clientRepository.save(client);
    }


    public void deleteClientById(int id) {
        if (!clientRepository.existsById(id)) {
            throw new RuntimeException("Client not found with ID: " + id);
        }
        clientRepository.deleteById(id);
    }
}
