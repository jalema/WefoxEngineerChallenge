## TITLE
ADR.2 - code structure

## STATUS
Accepted

## TAGS
code structure, hexagonal architecture, port and adapters

## CONTEXT
When we start a software project we need to decide how we will structure the code. There are many options, but the most 
common used are: Layered and Hexagonal Architecture.

## DECISION
I decide to use Hexagonal Architecture because it provides a very good separation between the business logic and the 
technology related code, and in this project there are many distinct technologies to integrate: kafka, database, rest, etc.
Each of the different layers proposed in hexagonal architecture has it own maven module to favour this separation of concerns.

## CONSECUENCES
Good:
* The changes in the technology layer doesn't affect to the business layer.
* Easy to change the business layer to other project or framework.
* Easy to test the use cases.
* Easy to develop the business layer without having to decide the technology upfront.
* Easy to parallelize the work between business and adapters.

Bad:
* More work in the beginning.