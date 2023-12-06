package engineering.james.kafkaexample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

import engineering.james.kafkaexample.events.Event;
import engineering.james.kafkaexample.events.UnrecognisedTopicException;

import static engineering.james.kafkaexample.events.EventFactory.generateEventForTopic;
import lombok.RequiredArgsConstructor;

@Component
@ConditionalOnProperty(prefix = "application", name = "producer.enabled")
@RequiredArgsConstructor
public class ProducerScheduler {
    private final Producer producer;

    @Value("${application.producer.kafka-topic}")
    private String kafkaTopic;

    private final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @Scheduled(fixedRate = 1000)
    public void produce() {
        this.logger.info("Sending event to {} topic", kafkaTopic);

        try {
            Event event = generateEventForTopic(this.kafkaTopic);
            this.producer.send(event);
        } catch (UnrecognisedTopicException e) {
            this.logger.error("Unable to generate event for topic", e);
        } catch (JsonProcessingException e) {
            this.logger.error("Failed to serialise CloudEvent", e);
        }
    }
}
