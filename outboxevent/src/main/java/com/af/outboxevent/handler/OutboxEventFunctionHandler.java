package com.af.outboxevent.handler;

import com.af.outboxevent.model.OutboxEvent;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.CosmosDBTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

@Component
public class OutboxEventFunctionHandler {

    @Autowired
    private Consumer<List<OutboxEvent>> outboxEventTrigger;

    @FunctionName("outboxEventTrigger")
    public void run(
            @CosmosDBTrigger(
                name = "cosmosTrigger",
                databaseName = "orders-db",
                containerName = "outboxEvents",
                connection = "CosmosDBConnectionString",
                leaseContainerName = "leases",
                createLeaseContainerIfNotExists = true
            ) List<OutboxEvent> items,
            final ExecutionContext context) {
        
        context.getLogger().info("Received " + items.size() + " outbox events from Cosmos DB change feed.");
        if (outboxEventTrigger != null) {
            outboxEventTrigger.accept(items);
        } else {
            context.getLogger().severe("outboxEventTrigger Consumer bean was not injected!");
        }
    }
}
