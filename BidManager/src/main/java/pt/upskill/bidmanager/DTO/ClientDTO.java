package pt.upskill.bidmanager.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import pt.upskill.bidmanager.Models.Client;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO extends RepresentationModel<ClientDTO> {
    private int id;
    private String name;
    private String email;

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.email = client.getEmail();
    }

}
