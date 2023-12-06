package engineering.james.kafkaexample.events;

import org.springframework.stereotype.Component;

@Component
public class EventFactory {
    private static final String RESOURCE_USAGE_EVENT_TYPE = "resource-usage";
    private static final String CREDIT_CHARGE_EVENT_TYPE = "credit-charge";

    public static Event generateEventForType(String type) throws UnrecognisedEventTypeException {
        switch (type) {
            case RESOURCE_USAGE_EVENT_TYPE:
                return new ResourceUsage();
            case CREDIT_CHARGE_EVENT_TYPE:
                return new ResourceUsage();
            default:
                throw new UnrecognisedEventTypeException(type);
        }
    }
}
