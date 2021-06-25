package rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String topic;
    private String replayTopic;
    private String action;

    public void setDetails(String topic, String replayTopic, String action) {
        this.topic = topic;
        this.replayTopic = replayTopic;
        this.action = action;
    }
}
