package engineering.james.kafkaexample;

import java.net.URI;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Producer {
    private final KafkaTemplate<String, CloudEvent> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public <T> CompletableFuture<SendResult<String, CloudEvent>> send(T payload) throws JsonProcessingException {
        CloudEvent event = CloudEventBuilder
                .v1()
                .withId(UUID.randomUUID().toString())
                .withType("com.example") // TODO: configure via props (+ topic)
                .withSource(URI.create("http://service")) // TODO: configure via props (+ topic)
                .withData(this.objectMapper.writeValueAsBytes(payload))
                .build();

        // TODO: configure topic via props
        return this.kafkaTemplate.send(new ProducerRecord<String, CloudEvent>("topic", event));
    }
}
