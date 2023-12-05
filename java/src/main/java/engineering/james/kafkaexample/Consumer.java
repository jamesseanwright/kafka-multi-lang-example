package engineering.james.kafkaexample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cloudevents.CloudEvent;
import static io.cloudevents.core.CloudEventUtils.mapData;

import java.lang.reflect.InvocationTargetException;

import io.cloudevents.core.data.PojoCloudEventData;
import io.cloudevents.jackson.PojoCloudEventDataMapper;
import lombok.RequiredArgsConstructor;

@Component
@ConditionalOnProperty(prefix = "application", name = "consumer.enabled")
@RequiredArgsConstructor
public class Consumer<T> {
    private final ObjectMapper objectMapper;
    private final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @Value("${application.consumer.event-data-class}")
    private String eventDataClass;

    @KafkaListener(topics = "${application.consumer.kafka-topic}")
    public void listen(CloudEvent event) {
        try {
            PojoCloudEventData<T> eventData = mapData(
                event,
                PojoCloudEventDataMapper.from(this.objectMapper, this.createDataClassInstance()));

            T data = eventData.getValue();

            this.logger.info("Received CloudEvent via Kafka:", data);
        } catch (Exception e) {
            this.logger.error("Error when deserialising CloudEvent", e);
        }
    }

    // Forgive me...
    @SuppressWarnings("unchecked")
    private Class<T> createDataClassInstance()
            throws ClassNotFoundException {
        return (Class<T>) Class.forName(this.eventDataClass);
    }
}
