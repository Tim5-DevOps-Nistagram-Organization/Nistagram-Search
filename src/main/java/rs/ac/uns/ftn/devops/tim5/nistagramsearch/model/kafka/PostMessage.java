package rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.kafka;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostMessage extends Message {
    private Long postId;
    private Long mediaId;
    private String username;
    private Set<String> tags;
}
