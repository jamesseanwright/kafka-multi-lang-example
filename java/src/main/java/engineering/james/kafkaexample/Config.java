package engineering.james.kafkaexample;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cloudevents.CloudEvent;
import io.cloudevents.kafka.CloudEventSerializer;

@Configuration
public class Config {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    // TODO: explain
    @Bean
    public KafkaTemplate<String, CloudEvent> kafkaTemplate(KafkaProperties kafkaProperties) {
        ProducerFactory<String, CloudEvent> factory = new DefaultKafkaProducerFactory<>(
                kafkaProperties.buildProducerProperties(null),
                new StringSerializer(),
                new CloudEventSerializer());

        return new KafkaTemplate<>(factory);
    }
}
