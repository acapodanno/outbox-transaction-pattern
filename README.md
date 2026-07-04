# Outbox Transaction Pattern Sequence Diagram

```mermaid
sequenceDiagram
    actor Cliente as Cliente
    participant kafka@{ "type" : "queue" } as kafka
    participant orderService as orderService
    participant outbox_events_function as outbox_events_function
    participant orders_db@{ "type" : "database" } as orders_db
    participant outbox_events_db@{ "type" : "database" } as outbox_events_db

Cliente ->> orderService: POST /orders
opt: transaction
    orderService ->> orders_db : saveOrder()
    orders_db -->> orderService: T
    orderService ->> outbox_events_db: saveOutboxEvent()
    outbox_events_db -->> orderService: T
end
outbox_events_db -->> outbox_events_function: changefeedCosmosDB
outbox_events_function ->> kafka: sendMessagge()
kafka ->>other_service: consumeMessage()
orderService -->> Cliente: 204 Created
```