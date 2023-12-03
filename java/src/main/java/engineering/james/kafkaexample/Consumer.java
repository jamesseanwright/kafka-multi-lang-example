package engineering.james.kafkaexample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cloudevents.CloudEvent;
import static io.cloudevents.core.CloudEventUtils.mapData;
import io.cloudevents.core.data.PojoCloudEventData;
import io.cloudevents.jackson.PojoCloudEventDataMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class Consumer<T> {
    protected abstract Class<T> getEventDataClass();

    private final ObjectMapper objectMapper;
    private final Logger logger = LoggerFactory.getLogger(Consumer.class);

    // TODO: configure args via props
    @KafkaListener(topics = "topic")
    public void listen(CloudEvent event) {
        PojoCloudEventData<T> eventData = mapData(
                event,
                PojoCloudEventDataMapper.from(this.objectMapper, this.getEventDataClass()));

        T data = eventData.getValue();

        this.logger.info("Received CloudEvent via Kafka:", data);
    }
}
