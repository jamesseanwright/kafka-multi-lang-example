package engineering.james.kafkaexample;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cloudevents.CloudEvent;
import io.cloudevents.CloudEventData;
import io.cloudevents.core.builder.CloudEventBuilder;
import io.cloudevents.jackson.PojoCloudEventDataMapper;
import io.cloudevents.rw.CloudEventDataMapper;
import static io.cloudevents.core.data.PojoCloudEventData.wrap;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Producer {
    private final KafkaTemplate<String, CloudEvent> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public <T> CompletableFuture<SendResult<String, CloudEvent>> send(T payload) throws JsonProcessingException {
        CloudEvent event = CloudEventBuilder
                .v1()
                .withId("uuid")
                .withType("com.example") // TODO: configure via props (+ topic)
                .withSource(URI.create("http://service")) // TODO: configure via props (+ topic)
                .withData(this.objectMapper.writeValueAsBytes(payload))
                .build();

        // TODO: configure topic via props
        return this.kafkaTemplate.send(new ProducerRecord<String, CloudEvent>("topic", event));
    }
}
