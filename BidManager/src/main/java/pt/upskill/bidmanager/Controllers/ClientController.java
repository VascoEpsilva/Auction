package pt.upskill.bidmanager.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pt.upskill.bidmanager.DTO.ClientDTO;
import pt.upskill.bidmanager.Mappers.ClientMapper;
import pt.upskill.bidmanager.Models.Client;
import pt.upskill.bidmanager.Services.ClientService;
import pt.upskill.bidmanager.Services.Interfaces.IClientService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    @Autowired
    private IClientService clientService;

    /**
     * Returns a paginated list of clients, converted to DTOs, with HATEOAS links.
     */

    @GetMapping
    public ResponseEntity<CollectionModel<ClientDTO>> getAllClients(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                    @RequestParam(name = "size", defaultValue = "10") int size)
    {

        Page<Client> clientPage = clientService.getAllClients(page, size);

        Page<ClientDTO> clientDtoPage = clientPage.map(ClientMapper::toClientDTO);

        List<ClientDTO> clientDtoList = clientDtoPage.getContent();

        clientDtoList.forEach(dto -> {
            Link selfLink = linkTo(methodOn(ClientController.class)
                    .getClientById(dto.getId()))
                    .withSelfRel();
            dto.add(selfLink);
        });

        List<Link> links = new ArrayList<>();

        Link selfLink = linkTo(methodOn(ClientController.class)
                .getAllClients(page, size))
                .withSelfRel();
        links.add(selfLink);


        if (clientDtoPage.hasNext()) {
            Link nextLink = linkTo(methodOn(ClientController.class)
                    .getAllClients(page + 1, size))
                    .withRel("next");
            links.add(nextLink);
        }

        if (clientDtoPage.hasPrevious()) {
            Link prevLink = linkTo(methodOn(ClientController.class)
                    .getAllClients(page - 1, size))
                    .withRel("previous");
            links.add(prevLink);
        }

        CollectionModel<ClientDTO> collectionModel = CollectionModel.of(clientDtoList, links);

        return clientDtoList.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable int id) {
        Client client = clientService.getClientById(id);
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ClientDTO clientDTO = ClientMapper.toClientDTO(client);

        Link selfLink = linkTo(methodOn(ClientController.class)
                .getClientById(id))
                .withSelfRel();
        clientDTO.add(selfLink);

        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }

    /**
     *  Create (register) a new Client
     */
    @PostMapping("/Register")
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO clientDTO) {
        Client newClient = ClientMapper.toClient(clientDTO);

        Client savedClient = clientService.registerClient(newClient);

        ClientDTO savedClientDTO = ClientMapper.toClientDTO(savedClient);

        Link selfLink = linkTo(methodOn(ClientController.class)
                .getClientById(savedClientDTO.getId()))
                .withSelfRel();
        savedClientDTO.add(selfLink);

        return new ResponseEntity<>(savedClientDTO, HttpStatus.CREATED);
    }



    /**
     * Get one Client by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientByID(@PathVariable int id) {
        try {
            Client client = clientService.getClientById(id);
            ClientDTO dto = ClientMapper.toClientDTO(client);

            Link selfLink = linkTo(methodOn(ClientController.class)
                    .clientService.getClientById(id))
                    .withSelfRel();
            dto.add(selfLink);


            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable int id,
                                                  @RequestBody ClientDTO clientDTO) {
        Client existingClient = clientService.getClientById(id);

        if(existingClient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        existingClient.setName(clientDTO.getName());
        existingClient.setEmail(clientDTO.getEmail());

        Client updatedClient = clientService.updateClient(existingClient);

        ClientDTO updatedDTO = ClientMapper.toClientDTO(updatedClient);

        Link selfLink = linkTo(methodOn(ClientController.class)
                .getClientById(updatedDTO.getId()))
                .withSelfRel();
        updatedDTO.add(selfLink);

        return new ResponseEntity<>(updatedDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClientById(@PathVariable int id) {
        try{
            clientService.deleteClientById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}


