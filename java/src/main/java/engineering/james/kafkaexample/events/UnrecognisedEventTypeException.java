package engineering.james.kafkaexample.events;

public class UnrecognisedEventTypeException extends RuntimeException {
    public UnrecognisedEventTypeException(String eventType) {
        super(String.format("%s is not a recognised event type", eventType));
    }
}
