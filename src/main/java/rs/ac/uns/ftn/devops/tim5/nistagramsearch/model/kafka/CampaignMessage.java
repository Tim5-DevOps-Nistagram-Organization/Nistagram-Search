package rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.kafka;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Advertisement;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.enums.CampaignEnum;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CampaignMessage extends Message {

    private Long campaignId;
    private Date startDate;
    private Date endDate;
    private int numShowsPerDay;
    @Enumerated(EnumType.STRING)
    private CampaignEnum type;
    private String agentUsername;
    private Collection<Advertisement> advertisements;

}
