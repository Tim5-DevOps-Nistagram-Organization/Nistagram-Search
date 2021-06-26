package rs.ac.uns.ftn.devops.tim5.nistagramsearch.kafka.saga;


import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.kafka.Constants;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.kafka.Message;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.kafka.UserMessage;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.UserService;

@Service
public class Consumer {

    private final UserService userService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Gson gson;

    @Autowired
    public Consumer(UserService userService, KafkaTemplate<String, String> kafkaTemplate, Gson gson) {
        this.userService = userService;
        this.kafkaTemplate = kafkaTemplate;
        this.gson = gson;
    }

    @KafkaListener(topics = Constants.SEARCH_TOPIC, groupId = Constants.GROUP)
    public void getMessage(String msg) {
        Message message = gson.fromJson(msg, Message.class);
        if (message.getReplayTopic().equals(Constants.USER_ORCHESTRATOR_TOPIC)) {
            UserMessage userMessage = gson.fromJson(msg, UserMessage.class);
            if (userMessage.getAction().equals(Constants.START_ACTION)) {
                try {
                    userService.create(userMessage.getUsername(), userMessage.getEmail());
                    userMessage.setDetails(userMessage.getReplayTopic(), Constants.SEARCH_TOPIC, Constants.DONE_ACTION);
                } catch (Exception e) {
                    userMessage.setDetails(userMessage.getReplayTopic(), Constants.SEARCH_TOPIC, Constants.ERROR_ACTION);
                }
            }
            kafkaTemplate.send(userMessage.getTopic(), gson.toJson(userMessage));
        }
    }
}