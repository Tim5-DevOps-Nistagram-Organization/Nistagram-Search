package rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.kafka;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContentReportMessage extends Message {

    private Long unappropriatedContentId;
    private String reportDescription;
    private Long postId;
    private String username;

}
