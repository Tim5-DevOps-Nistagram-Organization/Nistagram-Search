package rs.ac.uns.ftn.devops.tim5.nistagramsearch.service;

import rs.ac.uns.ftn.devops.tim5.nistagramsearch.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Campaign;

import java.util.Collection;

public interface CampaignService {

    Campaign add(Campaign campaign) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;

    Collection<Campaign> getAllActiveByAgent(String agentUsername);


}
