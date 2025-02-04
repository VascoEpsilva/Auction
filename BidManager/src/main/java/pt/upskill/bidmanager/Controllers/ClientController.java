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

        // Convert to DTO
        ClientDTO clientDTO = ClientMapper.toClientDTO(client);

        //
        Link selfLink = linkTo(methodOn(ClientController.class)
                .getClientById(id))
                .withSelfRel();
        clientDTO.add(selfLink);

        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }
}


