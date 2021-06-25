package rs.ac.uns.ftn.devops.tim5.nistagramsearch.saga;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.dto.PostSearchDTO;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostMessage implements Serializable {
    private String topic;
    private String replayTopic;
    private String action;
    private PostSearchDTO data;

    void setDetails(String topic, String replayTopic, String action) {
        this.topic = topic;
        this.replayTopic = replayTopic;
        this.action = action;
    }
}
