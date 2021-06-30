package rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.kafka;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FollowMessage extends Message {
    private String userUsername;
    private String followingUsername;
    private String followAction;

}
