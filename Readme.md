Spring Cloud Netflix Eureka

Eureka know the every instance of port and serve as main router


Request -> Api gatewaty -> Eureka -> microservices


Axon Framework and axon server

Choreography

Orchestration 

CQRS => Command Query Responsibility Segregatoin



Eureka Discover server will know eureka discovery client automatically which instance was running

api gateway sit as main router, validation, logging, specific filter, it have buid in load balancer 

stop at D:\Video\Spring Boot Microservices, CQRS, SAGA, Axon Framework\[TutsNode.com] - Spring Boot Microservices, CQRS, SAGA, Axon Framework\5. Spring Cloud API Gateway & Load Balancing

serve.port =0 => random port number


Download Axon server

D:\Video\Spring Boot Microservices, CQRS, SAGA, Axon Framework\[TutsNode.com] - Spring Boot Microservices, CQRS, SAGA, Axon Framework\6. Axon Server - Getting Started

Axon config file
https://docs.axoniq.io/reference-guide/axon-server/administration/admin-configuration/configuration


-p publish  8124 port number on left side is local machine :8124 port no. on right side is docker container 

-v path to local storage :/data path to the folder(storage) in docker container 

docker will find docker repository 

docker run 	--name axonserver -p 8024:8024 -p 8124:8124 -v "D:\projects\MircoServices\docker-data\data":/data -v "D:\projects\MircoServices\docker-data\eventdata":/eventdata -v "D:\projects\MircoServices\docker-data\config":/config axoniq/axonserver


docker ps -a

docker stop containerId
docker rm containerId



Create Command Pattern
Verb + Noun + Command


Client App <=Command/Reply> Command Bus <=> Command Handler <=> Domain <=Events> Repository <=Events> Event Store

Product Aggregate will hold the current state of product object

Aggregate class is core of microservices, It is domain object hold the current state of main object

Aggregate product will be dispatch it will be method in aggregate class

every time one state of product info change event sourcing method will be invoked

when product update command received state of this prodcut aggregate object will first need to be loaded from event store 
action framework will first create new empty object of product aggregate that is why it need empty constructor 
it will read every single event that is stored in event store for this aggregate and it will apply event to aggregate


In aggregate class => second handler will be used as command handler method and it will be invoked when create product command is dispatched


Noun + Performed Action + Event

event handler are used to update aggregate state called event source in handling method 

@AggregateIdentifier in productaggregate associate dispatch @TargetAggregateIdentifier in createProductCommand



D:\Video\Spring Boot Microservices, CQRS, SAGA, Axon Framework\[TutsNode.com] - Spring Boot Microservices, CQRS, SAGA, Axon Framework\7. CQRS Pattern. Products Microservice
13. @EventSourcingHandler need to read again


If we are using axon with spring cloud => maven start demanding google guava to be added in dependencies


to test on tomorrow =>
ProductController
response = commandGateway.sendAndWait(createProductCommand);
set break point and running as debug mode 


ProductAggregate set break point for throw exception
and aggregateLifecycle.apply


Productcontroller => pom.xml => add spring boot data jpa starter dependencies
add h2 or mysql up to you

D:\AA Backup\Video\Spring Boot Microservices, CQRS, SAGA, Axon Framework\[TutsNode.com] - Spring Boot Microservices, CQRS, SAGA, Axon Framework\7. CQRS Pattern. Products Microservice


V + N + Command
CreateProductCommand

N + PerformedAction + Event
ProductCreatedEvent

D:\AA Backup\Video\Spring Boot Microservices, CQRS, SAGA, Axon Framework\[TutsNode.com] - Spring Boot Microservices, CQRS, SAGA, Axon Framework\8. CQRS. Persisting Event in the Products database




Stop at here 6-May-2022
D:\AA Backup\Video\Spring Boot Microservices, CQRS, SAGA, Axon Framework\[TutsNode.com] - Spring Boot Microservices, CQRS, SAGA, Axon Framework\9. CQRS. Querying Data


1st start axon server
2nd edureka discovery 
3rd product service
4th Api Gateway


Core package share between query and command api


Query class start with Find+XXX+Query


Request => Java Bean => Java Bean Validation =>  Controller => Command Gateway --> Dispatch => Command Bus <= Command Handler --> Dispatch => Event Bus <= Event Handler

Command Handler validation => 

Message dispatch interceptor => when a command is dispatched on command bus
=> intercept between Command gatewaty and command bus => can do additional logging, validation


Stop at here 12-June-2022
D:\AA Backup\Video\Spring Boot Microservices, CQRS, SAGA, Axon Framework\[TutsNode.com] - Spring Boot Microservices, CQRS, SAGA, Axon Framework\12. Validation. Message Dispatch Interceptor


interceptor need to register in spring config

if added interceptor in interceptor throw exception it will not go to commandhandler Aggregate class 


Stop at here 6-July-2022
D:\AA Backup\Video\Spring Boot Microservices, CQRS, SAGA, Axon Framework\[TutsNode.com] - Spring Boot Microservices, CQRS, SAGA, Axon Framework\13. Validation. How to check if record exists
Set based Constitency validation

how to check if product is existing product
when creating new product client api first assist an event in event store and only after the event is persisted
and it will publish an event to synchronized data between the event store and query the database
communication bet command and query api is done via event messaging

sync bet two models might take sometimes because of event messaging

command api quickly check if record already exists before persisted in event store

Solution to this problem is
Axon framework set based constituency validation


Command model can contain any form of data model, introduce a small lookup table which contain productId and title
when the prouct create, command api will first lookup in lookup table
lookup table should not contain exact same product as in database
lookup table only store product fields that are need to look up table and see if exists (eg. id and title)

how to query look up table before command handler process command

message dispatcher will intercept the command before it is processed by command handler method
if exist in lookup table then command will be blocked

Creating product lookup table should be in separate datbase for testing purpose it can be in same databaase

@ProcessingGroup("product-group") in event Handler axon will create separate tracking event processor, use special tracking token
same event and different thread and node 


14 July-2022 stop at here
D:\AA Backup\Video\Spring Boot Microservices, CQRS, SAGA, Axon Framework\[TutsNode.com] - Spring Boot Microservices, CQRS, SAGA, Axon Framework\14. Handle Error & Rollback Transaction