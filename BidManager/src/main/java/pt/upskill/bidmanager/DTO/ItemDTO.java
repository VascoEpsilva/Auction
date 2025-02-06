package pt.upskill.bidmanager.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    public int id;

    public String name;

    public String description;

    public float price;

    public Boolean IsAvailable;

    public int CategoryId;

    public String categoryName;
}
