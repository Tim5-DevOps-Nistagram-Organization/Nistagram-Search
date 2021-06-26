package rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.kafka;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserMessage extends Message {
    private String username;
    private String email;

    public UserMessage(String topic, String replayTopic, String action, String username, String email) {
        super(topic, replayTopic, action);
        this.username = username;
        this.email = email;
    }
}
