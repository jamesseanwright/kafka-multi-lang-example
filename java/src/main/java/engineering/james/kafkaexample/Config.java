package engineering.james.kafkaexample;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cloudevents.CloudEvent;
import io.cloudevents.kafka.CloudEventSerializer;

import java.util.Map;
import java.util.HashMap;

@Configuration
public class Config {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
