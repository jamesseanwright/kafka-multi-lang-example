package engineering.james.kafkaexample.events;

import java.util.UUID;

public record CreditCharge(
    UUID accountId,
    String resourceType,
    double cost
) implements Event {

}
