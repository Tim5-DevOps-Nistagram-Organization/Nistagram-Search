package rs.ac.uns.ftn.devops.tim5.nistagramsearch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.kafka.Constants;

@Configuration
public class KafkaConfig {

    @Bean
    public ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate(
            ProducerFactory<String, String> producerFactory,
            ConcurrentKafkaListenerContainerFactory<String, String> factory) {
        ConcurrentMessageListenerContainer<String, String> replyContainer = factory.createContainer(Constants.AUTH_SEARCH_TOPIC);
        replyContainer.getContainerProperties().setMissingTopicsFatal(false);
        replyContainer.getContainerProperties().setGroupId(Constants.GROUP);
        return new ReplyingKafkaTemplate<>(producerFactory, replyContainer);
    }
}
