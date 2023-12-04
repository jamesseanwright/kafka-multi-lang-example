package engineering.james.kafkaexample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cloudevents.CloudEvent;
import static io.cloudevents.core.CloudEventUtils.mapData;
import io.cloudevents.core.data.PojoCloudEventData;
import io.cloudevents.jackson.PojoCloudEventDataMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@ConditionalOnProperty(prefix = "application", name = "consumer.enabled")
public class Consumer<T> {
    private final ObjectMapper objectMapper;
    private final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @Value("${application.consumer.event-data-class}")
    private Class<T> cls;

    @KafkaListener(topics = "${application.consumer.kafka-topic}")
    public void listen(CloudEvent event) {
        PojoCloudEventData<T> eventData = mapData(
                event,
                PojoCloudEventDataMapper.from(this.objectMapper, cls));

        T data = eventData.getValue();

        this.logger.info("Received CloudEvent via Kafka:", data);
    }
}
