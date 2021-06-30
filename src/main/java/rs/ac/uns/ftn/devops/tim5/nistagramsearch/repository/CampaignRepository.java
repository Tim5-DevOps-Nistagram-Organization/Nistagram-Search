package rs.ac.uns.ftn.devops.tim5.nistagramsearch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Campaign;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    @Query("SELECT r FROM Campaign r WHERE r.campaignId = ?1")
    Campaign findByOriginalCampaignId(Long id);
}
