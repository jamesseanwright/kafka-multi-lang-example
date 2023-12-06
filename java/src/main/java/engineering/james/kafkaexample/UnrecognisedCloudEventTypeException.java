package engineering.james.kafkaexample;

public class UnrecognisedCloudEventTypeException extends Exception {
  public UnrecognisedCloudEventTypeException(String cloudEventType) {
    super(String.format("%s is not a recognised CloudEvent type", cloudEventType));
  }
}
