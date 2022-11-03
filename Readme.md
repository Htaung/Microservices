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

When exceptions are thrown in event, default behavior is to look for an error msg and continue the execution
coz code and command handler was successfully executed => app will recieved a successful response 

If an exception is thrown and was not handled in event handler method -> it will not prevent all event handlers from execution
But execution will continue and the code in other method in same process and group will continue

exception msg will be thrown in event handler method will also not be handled by try catch in controller class or in 
centralized controller advice exception handler class.

If you want exception msg to propagate all the way to a controller method or to a controller advice class 
need to write additional code to configure the event processor to propagate the exception so you can catch in differenct place 
To do that we will use listener invocation class handler provied by axon framework
If doing this execution in other event handler method handle an error in controller class and return a custom error msg
txn will roll back 
For this to work => need to configure our event and method to be in same processing group that is configured to use subscribing event processor.
Event processor is component that is incharge of managing technical aspect of providing the event handler 

Event processor have 2 forms
Tracking event procesor 
Pull their msg from a source using a thread it managed itself. A different thread
Will retry processing the event using incremental back-off period 
processor will go into error mode, will release the token and retry an event as an incremental interval starting at one second 
will double after each attempt and to a maximum wait time of 60 seconds per attempt is achieved
this back-off time ensures that if another node is able to process event successfully, it will have opportunity to claim the token 
requried to process the event

    
Subscribing event processor 
Subscribing themselves to source of events and are invoked by thread managed by publishing mechanism. Same Thread
By default exception raised by event handlers are locked and processing will continue next event
will report the publication error to the component that provided an event
will have an exception bubbled up to the publishing component of event allowing to deal with it accordingly

If we propagate the exception all the way up, we can make entire txn will roll back
we will use subscribing event processor to manage event handler in product event handler class

17 july 2022 stop
D:\AA Backup\Video\Spring Boot Microservices, CQRS, SAGA, Axon Framework\[TutsNode.com] - Spring Boot Microservices, CQRS, SAGA, Axon Framework\14. Handle Error & Rollback Transaction\6. Trying to handle the @CommandExecutionException


import org.axonframework.messaging.interceptors.ExceptionHandler;

handle exception that are thrown from event handling functions in the same class

To do rollback and undo db changes made by event handler method
if processing group is configured to use subscribing event processor when using try catch event processor boz
event handler method is use subscribing event processor , these events are processed in same thread and processing events
in same thread gives this posibility to roll back the whole txn. 
if exception happen default behavior is to handle exception and continue execution.

one way is to use create own exception that catch exception thrown in event and methods
Create events exceptio handler and implement Listener invocation of axon (ListenerInvocationErrorHandler)
this will be last rethrow to make txn roll back (propagating error handler)

register listerinvocation 

25 july 2022
D:\AA Backup\Video\Spring Boot Microservices, CQRS, SAGA, Axon Framework\[TutsNode.com] - Spring Boot Microservices, CQRS, SAGA, Axon Framework\14. Handle Error & Rollback Transaction

10. Trying how transaction rollback works

2-Aug-2022
D:\AA Backup\Video\Spring Boot Microservices, CQRS, SAGA, Axon Framework\[TutsNode.com] - Spring Boot Microservices, CQRS, SAGA, Axon Framework\15. Assignment. Orders Microservice