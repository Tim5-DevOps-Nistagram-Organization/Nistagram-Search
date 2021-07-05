package rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Advertisement;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Campaign;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.User;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.AdvertismentService;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.CampaignService;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AdvertismentServiceImpl implements AdvertismentService {

    private final UserService userService;
    private final CampaignService campaignService;

    @Autowired
    public AdvertismentServiceImpl(UserService userService,
                                   CampaignService campaignService) {
        this.userService = userService;
        this.campaignService = campaignService;
    }

    @Override
    public Collection<Advertisement> getAdvertismentByUser(String username, double numHomeVisit) throws ResourceNotFoundException {
        Collection<Advertisement> retVal = new ArrayList<>();
        Collection<Campaign> allCampaign = new ArrayList<Campaign>();
        User user = userService.findByUsername(username);
        for(User followed : user.getFollowing()) {
            if (followed.getWebsiteUrl() != null) {
                allCampaign.addAll(campaignService.getAllActiveByAgent(followed.getUsername())
                        .stream()
                        .filter(campaign -> campaign.getNumShowsPerDay() > numHomeVisit)
                        .collect(Collectors.toList()));
            }
        }
        for(Campaign campaign: allCampaign) {
            retVal.addAll(campaign.getAdvertisements());
        }
        return retVal;
    }
}
