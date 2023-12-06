package engineering.james.kafkaexample.events;

public class UnrecognisedTopicException extends RuntimeException {
    public UnrecognisedTopicException(String topic) {
        super(String.format("%s is not a recognised topic", topic));
    }
}
