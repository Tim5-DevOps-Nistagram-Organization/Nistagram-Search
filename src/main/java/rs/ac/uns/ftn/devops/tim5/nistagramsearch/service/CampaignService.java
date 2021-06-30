package rs.ac.uns.ftn.devops.tim5.nistagramsearch.service;

import rs.ac.uns.ftn.devops.tim5.nistagramsearch.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Campaign;

public interface CampaignService {

    Campaign findById(Long id) throws ResourceNotFoundException;
    Campaign add(Campaign campaign) throws ResourceNotFoundException;
    void delete(Long id) throws ResourceNotFoundException;
}
