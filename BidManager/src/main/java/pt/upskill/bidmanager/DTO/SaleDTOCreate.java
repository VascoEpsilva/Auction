package pt.upskill.bidmanager.DTO;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTOCreate {
    public Date saleDate;
    public float salePrice;
    public int ItemId;
}
