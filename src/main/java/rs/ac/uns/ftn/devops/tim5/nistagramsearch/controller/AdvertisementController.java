package rs.ac.uns.ftn.devops.tim5.nistagramsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.dto.AdvertisementDTO;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.mapper.AdvertisementMapper;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.AdvertisementService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/adds")
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @Autowired
    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @GetMapping
    public ResponseEntity<Collection<AdvertisementDTO>> getAdds(@RequestParam int numOfVisit,
                                                                Principal principal) throws ResourceNotFoundException {

        Collection<AdvertisementDTO> retVal = new ArrayList<>();
        if (principal != null) {
            retVal = advertisementService.getAdvertisementByUser(principal.getName(), numOfVisit)
                    .stream().map(AdvertisementMapper::toDTO)
                    .collect(Collectors.toList());
        }
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

}
