package engineering.james.kafkaexample.events;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class EventFactory {
    private static final String RESOURCE_USAGE_EVENT_TYPE = "resource-usage";
    private static final String CREDIT_CHARGE_EVENT_TYPE = "credit-charge";

    public static Event generateEventForType(String type) throws UnrecognisedEventTypeException {
        switch (type) {
            case RESOURCE_USAGE_EVENT_TYPE:
                return new ResourceUsage(
                    UUID.fromString("a787e13b-0485-4ad8-b32d-da684aa15ab1"),
                    "My pipeline",
                    0.3 + Math.random() * (5 - 0.3)
                );
            case CREDIT_CHARGE_EVENT_TYPE:
                return new CreditCharge(
                    UUID.fromString("a787e13b-0485-4ad8-b32d-da684aa15ab1"),
                    "dpc-agent-compute",
                    1 + Math.random() * (20 - 1)
                );
            default:
                throw new UnrecognisedEventTypeException(type);
        }
    }
}
