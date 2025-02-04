package pt.upskill.bidmanager.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import pt.upskill.bidmanager.DTO.ClientDTO;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<Bid> bids;

    public Client(ClientDTO clientDTO){
        this.name = clientDTO.getName();
        this.email = clientDTO.getEmail();
        this.bids = new ArrayList<>();
    }
}
