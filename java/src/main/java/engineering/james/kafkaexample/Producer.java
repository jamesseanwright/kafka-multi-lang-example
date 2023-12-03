package engineering.james.kafkaexample;

import java.net.URI;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${application.cloudevents.type}")
    private final String eventType;

    @Value("${application.cloudevents.source}")
    private final String eventSource;

    @Value("${application.kafka.topic}")
    private final String topic;

    public <T> CompletableFuture<SendResult<String, CloudEvent>> send(T payload) throws JsonProcessingException {
        CloudEvent event = CloudEventBuilder
                .v1()
                .withId(UUID.randomUUID().toString())
                .withType(this.eventType)
                .withSource(URI.create(this.eventSource))
                .withData(this.objectMapper.writeValueAsBytes(payload))
                .build();

        return this.kafkaTemplate.send(new ProducerRecord<String, CloudEvent>(this.topic, event));
    }
}
