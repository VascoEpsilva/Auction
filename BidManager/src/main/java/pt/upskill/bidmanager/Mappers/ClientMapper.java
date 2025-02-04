package pt.upskill.bidmanager.Mappers;

import pt.upskill.bidmanager.DTO.ClientDTO;
import pt.upskill.bidmanager.Models.Client;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ClientMapper {

    public static ClientDTO toClientDTO(Client client) {
        if (client != null){
            return new ClientDTO(client);
        }
        return null;
    }

    public static Client toClient(ClientDTO clientDTO) {
        if (clientDTO != null){
            Client client = new Client();
            client.setName(clientDTO.getName());
            client.setEmail(clientDTO.getEmail());
            return new Client();
        }
        return null;
    }

    public static Client toClientForUpdate(ClientDTO clientDTO, Client existingClient) {
        if (clientDTO == null || existingClient == null) {
            return null;
        }
        existingClient.setName(clientDTO.getName());
        existingClient.setEmail(clientDTO.getEmail());
        return existingClient;
    }

    public static List<ClientDTO> toClientDTOList(List<Client> clients) {
        if (clients == null) {
            return Collections.emptyList();
        }

        return clients.stream()
                .map(ClientMapper::toClientDTO)
                .collect(Collectors.toList());
    }

}
