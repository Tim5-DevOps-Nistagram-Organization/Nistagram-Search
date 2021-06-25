package rs.ac.uns.ftn.devops.tim5.nistagramsearch.saga;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.dto.PostSearchDTO;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.mapper.PostMapper;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Post;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.PostService;

@Service
public class Consumer {

    private final PostService postService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Gson gson;

    @Autowired
    public Consumer(PostService postService, KafkaTemplate<String, String> kafkaTemplate, Gson gson) {
        this.postService = postService;
        this.kafkaTemplate = kafkaTemplate;
        this.gson = gson;
    }

    @KafkaListener(topics = Constants.SEARCH_TOPIC, groupId = "my_group_id")
    public void getMessage(String msg) {
        PostMessage message = gson.fromJson(msg, PostMessage.class);
        if (message.getReplayTopic().equals(Constants.POST_ORCHESTRATOR_TOPIC) &&
                message.getAction().equals(Constants.START_ACTION)) {
            try {
                PostSearchDTO postSearchDTO = message.getData();
                Post post = PostMapper.toEntity(postSearchDTO);
                postService.addNewPost(post);
                message.setDetails(message.getReplayTopic(), Constants.SEARCH_TOPIC, Constants.DONE_ACTION);
                kafkaTemplate.send(message.getTopic(), gson.toJson(message));
            } catch (Exception e) {
                message.setDetails(message.getReplayTopic(), Constants.SEARCH_TOPIC, Constants.ERROR_ACTION);
                kafkaTemplate.send(message.getTopic(), gson.toJson(message));
            }
        }
    }
}
