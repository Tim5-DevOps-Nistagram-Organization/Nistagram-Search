package rs.ac.uns.ftn.devops.tim5.nistagramsearch.service;

import rs.ac.uns.ftn.devops.tim5.nistagramsearch.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Advertisement;

import java.util.Collection;

public interface AdvertisementService {

    Collection<Advertisement> getAdvertisementByUser(String username, double numHomeVisit) throws ResourceNotFoundException;

}
