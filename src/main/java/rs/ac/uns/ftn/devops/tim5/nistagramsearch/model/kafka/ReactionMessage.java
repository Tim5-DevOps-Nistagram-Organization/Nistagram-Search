package rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.kafka;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReactionMessage extends Message {

    private Long reactionId;
    private int reactionValue;
    private Long postId;
    private String username;

}
