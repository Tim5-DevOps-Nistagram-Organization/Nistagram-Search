package rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.kafka;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContentReportMessage extends Message{

    private Long unappropriatedContentId;
    private String reportDescription;
    private Long postId;
    private String username;

    public ContentReportMessage(String topic, String replayTopic, String action,
                                Long unappropriatedContentId, String reportDescription,
                                Long postId, String username) {
        super(topic, replayTopic, action);
        this.postId = postId;
        this.unappropriatedContentId = unappropriatedContentId;
        this.username = username;
        this.reportDescription = reportDescription;
    }
}
