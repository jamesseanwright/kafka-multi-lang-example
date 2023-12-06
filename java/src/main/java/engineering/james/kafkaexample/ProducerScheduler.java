package engineering.james.kafkaexample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import engineering.james.kafkaexample.events.Event;

import static engineering.james.kafkaexample.events.EventFactory.generateEventForType;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProducerScheduler {
    private final Producer producer;

    @Value("${application.producer.event-data-type}")
    private String eventType;

    @Scheduled(fixedRate = 1000)
    public void produce() throws Exception {
        // TODO: handle JsonProcessingException & factory exception
        // TODO: await and log success + failure
        Event event = generateEventForType(eventType);
        this.producer.send(event);
    }
}
