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
    private String websiteUrl;

}
