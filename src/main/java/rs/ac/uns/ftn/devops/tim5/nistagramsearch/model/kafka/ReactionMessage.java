package rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.kafka;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class ReactionMessage extends Message{

    private Long reactionId;
    private int reactionValue;
    private Long postId;
    private String username;

    public ReactionMessage(String topic, String replayTopic, String action,
                           Long reactionId, int reactionValue,
                           Long postId, String username) {
        super(topic, replayTopic, action);
        this.postId = postId;
        this.reactionId = reactionId;
        this.username = username;
        this.reactionValue = reactionValue;
    }

}
