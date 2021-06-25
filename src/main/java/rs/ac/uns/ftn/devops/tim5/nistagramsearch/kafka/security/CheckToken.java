package rs.ac.uns.ftn.devops.tim5.nistagramsearch.kafka.security;

import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.kafka.Constants;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.kafka.UserSecurity;

import java.util.concurrent.ExecutionException;

@Component
public class CheckToken {

    private final ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate;
    private final Gson gson;

    @Autowired
    public CheckToken(ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate, Gson gson) {
        this.replyingKafkaTemplate = replyingKafkaTemplate;
        this.gson = gson;
    }

    public UserSecurity check(String token) throws ExecutionException, InterruptedException {
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(Constants.AUTH_TOPIC, token);
        RequestReplyFuture<String, String, String> future = replyingKafkaTemplate.sendAndReceive(producerRecord);
        ConsumerRecord<String, String> consumerRecord = future.get();
        return gson.fromJson(consumerRecord.value(), UserSecurity.class);
    }
}
