package engineering.james.kafkaexample.events;

import java.util.UUID;

public record CreditCharge(
    UUID accountId,
    String resourceType,
    double cost
) implements Event {
    @Override
    public String toString() {
        return String.format(
            "CreditCharge[accountID=%s, resourceType=%s, cost=%f]",
            this.accountId(),
            this.resourceType(),
            this.cost()
        );
    }
}
