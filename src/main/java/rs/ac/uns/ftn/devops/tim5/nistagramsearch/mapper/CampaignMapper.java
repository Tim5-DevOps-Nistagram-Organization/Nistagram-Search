package rs.ac.uns.ftn.devops.tim5.nistagramsearch.mapper;

import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Campaign;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.User;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.kafka.CampaignMessage;

public class CampaignMapper {

    private CampaignMapper() {
    }

    public static Campaign toEntity(CampaignMessage campaignMessage) {
        return new Campaign(null,
                campaignMessage.getCampaignId(),
                campaignMessage.getStartDate(),
                campaignMessage.getEndDate(),
                campaignMessage.getNumShowsPerDay(),
                campaignMessage.getType(),
                new User(campaignMessage.getAgentUsername()),
                campaignMessage.getAdvertisements());
    }

}
