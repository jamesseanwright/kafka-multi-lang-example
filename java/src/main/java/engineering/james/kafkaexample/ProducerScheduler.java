package engineering.james.kafkaexample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import engineering.james.kafkaexample.events.Event;

import static engineering.james.kafkaexample.events.EventFactory.generateEventForTopic;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProducerScheduler {
    private final Producer producer;

    @Value("${application.producer.kafka-topic}")
    private String kafkaTopic;

    @Scheduled(fixedRate = 1000)
    public void produce() throws Exception {
        // TODO: handle JsonProcessingException & factory exception
        // TODO: await and log success + failure
        Event event = generateEventForTopic(this.kafkaTopic);
        this.producer.send(event);
    }
}
