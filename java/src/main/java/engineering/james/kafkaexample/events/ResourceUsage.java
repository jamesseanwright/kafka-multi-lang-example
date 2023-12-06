package engineering.james.kafkaexample.events;

import java.util.UUID;

public record ResourceUsage(UUID accountId, String pipelineName, double taskHours)
    implements Event {
  @Override
  public String toString() {
    return String.format(
        "ResourceUsage[accountID=%s, pipelineName=%s, taskHours=%f]",
        this.accountId(), this.pipelineName(), this.taskHours());
  }
}
