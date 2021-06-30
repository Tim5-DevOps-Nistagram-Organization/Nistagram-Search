package rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Campaign;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.repository.CampaignRepository;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.CampaignService;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.UserService;

@Service
public class CampaignServiceImpl implements CampaignService {

    private CampaignRepository campaignRepository;
    private UserService userService;

    @Autowired
    public CampaignServiceImpl(CampaignRepository campaignRepository,
                               UserService userService){
        this.campaignRepository = campaignRepository;
        this.userService = userService;
    }

    @Override
    public Campaign findById(Long id) throws ResourceNotFoundException {
        return campaignRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Campaign"));
    }

    @Override
    public Campaign add(Campaign campaign) throws ResourceNotFoundException {
        campaign.setAgent(userService.findByUsername(campaign.getAgent().getUsername()));
        return campaignRepository.save(campaign);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        Campaign campaign = campaignRepository.findByOriginalCampaignId(id);
        campaignRepository.delete(campaign);
    }
}
