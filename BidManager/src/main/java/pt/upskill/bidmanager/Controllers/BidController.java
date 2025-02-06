package pt.upskill.bidmanager.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.upskill.bidmanager.DTO.BidDTO;
import pt.upskill.bidmanager.DTO.BidDTOCreate;
import pt.upskill.bidmanager.Services.BidService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bids")
public class BidController {

    @Autowired
    private BidService bidService;

    @PostMapping
    public ResponseEntity<BidDTO> createBid(@RequestBody BidDTOCreate createDTO) {
        try {
            BidDTO created = bidService.createBid(createDTO);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BidDTO> getBidById(@PathVariable int id) {
        try {
            BidDTO dto = bidService.getBidById(id);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<BidDTO>> getAllBids() {
        List<BidDTO> list = bidService.getAllBids();
        return list.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(list, HttpStatus.OK);
    }
}
