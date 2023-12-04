package engineering.james.kafkaexample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationTargetException;

@Component
@RequiredArgsConstructor
public class ProducerScheduler {
    private final Producer producer;

    @Value("${application.producer.event-data-class}")
    private String eventDataClass;

    @Scheduled(fixedRate = 1000)
    public void produce() throws Exception {
        // TODO: handle JsonProcessingException & exceptions thrown by instance method()
        // TODO: await and log success + failure
        this.producer.send(createPayloadClassInstance());
    }

    private Object createPayloadClassInstance()
            throws ClassNotFoundException, NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException {
        return Class.forName(this.eventDataClass).getConstructor().newInstance();
    }
}
