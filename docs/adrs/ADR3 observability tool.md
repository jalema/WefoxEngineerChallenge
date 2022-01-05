## TITLE
ADR.3 - observability tool

## STATUS
Accepted

## TAGS
observability, sleuth, zipkin, distributed tracing

## CONTEXT
When we decide to make a microservices ecosystem, one of the challenges are to know the bottlenecks and be able to detect 
in which of the pieces is the problem when we have an error. There are three pillars for observability: logs, traces and metrics.

In this case we decide to integrate a new tracing tool to know where the problem are and to know the overall performance.
There are many technologies in this space: Zipkin, Jaeger, Open Telemetry, etc. 

## DECISION
I decide to use Zipkin because I have previous experience, it has an easy integration with spring-boot through 
Sleuth, its Open Source and provides an easy to use GUI.

For simplicity, I decide to integrate using http, but zipkin support many other protocols and tools as: RabbitMQ, Kafka, etc.

## CONSECUENCES
Good:
* Provides a GUI with very useful information.
* Many option to store the information: databases, search engines, etc.

Bad:
* Not good performance.