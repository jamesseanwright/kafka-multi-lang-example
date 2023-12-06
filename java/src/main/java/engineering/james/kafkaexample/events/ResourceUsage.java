package engineering.james.kafkaexample.events;

import java.util.UUID;

public record ResourceUsage(
    UUID accountId,
    String pipelineName,
    double taskHours
) implements Event {

}
