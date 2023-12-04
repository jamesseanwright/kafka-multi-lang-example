package engineering.james.kafkaexample;

import java.net.URI;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;

import lombok.RequiredArgsConstructor;

@Component
@ConditionalOnProperty(prefix = "application", name = "producer.enabled")
@RequiredArgsConstructor
public class Producer {
    // TODO: consider providing custom bean for KafkaTemplate<String, CloudEvent>
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${application.producer.cloudevents.type}")
    private String eventType;

    @Value("${application.producer.cloudevents.source}")
    private String eventSource;

    @Value("${application.producer.kafka-topic}")
    private String topic;

    public <T> CompletableFuture<SendResult<String, Object>> send(T payload) throws JsonProcessingException {
        CloudEvent event = CloudEventBuilder
                .v1()
                .withId(UUID.randomUUID().toString())
                .withType(this.eventType)
                .withSource(URI.create(this.eventSource))
                .withData(this.objectMapper.writeValueAsBytes(payload))
                .build();

        return this.kafkaTemplate.send(new ProducerRecord<>(this.topic, event));
    }
}
