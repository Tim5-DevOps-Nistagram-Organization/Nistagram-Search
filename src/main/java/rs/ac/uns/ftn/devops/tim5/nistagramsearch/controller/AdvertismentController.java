package rs.ac.uns.ftn.devops.tim5.nistagramsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.dto.PostResponseDTO;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.mapper.AdvertismentMapper;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.AdvertismentService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/adds")
public class AdvertismentController {

    private final AdvertismentService advertismentService;

    @Autowired
    public AdvertismentController(AdvertismentService advertismentService) {
        this.advertismentService = advertismentService;
    }

    @GetMapping()
    public ResponseEntity<Collection<PostResponseDTO>> getAdds(@RequestParam int numOfVisit,
                                                            Principal principal) throws ResourceNotFoundException {

        Collection<PostResponseDTO> retVal = new ArrayList<PostResponseDTO>();
        if (principal != null) {
            retVal =  advertismentService.getAdvertismentByUser(principal.getName(), numOfVisit)
                    .stream().map(AdvertismentMapper::toSearchDTO)
                    .collect(Collectors.toList());
        }
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

}
