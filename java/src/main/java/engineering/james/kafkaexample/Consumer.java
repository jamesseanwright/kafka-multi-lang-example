package engineering.james.kafkaexample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import engineering.james.kafkaexample.events.CreditCharge;
import engineering.james.kafkaexample.events.ResourceUsage;
import io.cloudevents.CloudEvent;
import static io.cloudevents.core.CloudEventUtils.mapData;

import io.cloudevents.core.data.PojoCloudEventData;
import io.cloudevents.jackson.PojoCloudEventDataMapper;
import lombok.RequiredArgsConstructor;

@Component
@ConditionalOnProperty(prefix = "application", name = "consumer.enabled")
@RequiredArgsConstructor
public class Consumer {
    private final ObjectMapper objectMapper;
    private final Logger logger = LoggerFactory.getLogger(Consumer.class);

    private static final String RESOURCE_USAGE_CLOUD_EVENT_TYPE = "com.matillion.resource-usage";
    private static final String CREDIT_CHARGE_CLOUD_EVENT_TYPE = "com.matillion.credit-charge";

    @KafkaListener(topics = "${application.consumer.kafka-topic}")
    public void listen(CloudEvent event) {
        try {
            PojoCloudEventData<?> eventData = mapData(
                event,
                PojoCloudEventDataMapper.from(this.objectMapper, this.getEventDataClass(event.getType())));

            this.logger.info("Received CloudEvent via Kafka: {}", eventData.getValue().toString());
        } catch (Exception e) {
            this.logger.error("Error when deserialising CloudEvent", e);
        }
    }

    private Class<?> getEventDataClass(String cloudEventType) {
        switch (cloudEventType) {
            case RESOURCE_USAGE_CLOUD_EVENT_TYPE:
                return ResourceUsage.class;

            case CREDIT_CHARGE_CLOUD_EVENT_TYPE:
                return CreditCharge.class;

            default:
                // TODO: more specific/customer exception
                throw new IllegalArgumentException();
        }
    }
}
