# Assignment 8 - Message Oriented Middleware (MOM)

## Banking Operations
Create MOM-integrated application used by a customer, who need an urgent bank loan.
The customer application
- makes a loan requests to multiple banks
- collects non-binding bank quotes from (simulated) bank applications
- compares the quotes and selects one, based on its own financial status and criteria
See [here]("https://www.gocompare.com/loans/") for inspiration.

#### The solution:
A simple proof of concept containing:
1. An exchange for requesting bank quotes of type fanout.
2. An exchange for receiving bank quotes of type direct.

a) The client starts by sending a message on to the requesting exchange.\
b) The requesting exchange is a fanout, distributing messages to every bound queue.\
c) Every bank establishes its own queue on the requesting exchange.\
d) The banks each receive a message from the requesting exchange.\
e) Any bank will then return a quote on to the receiving exchange by sending a message with a routing key.\
f) The client listens for messages on the queue of the receiving exchange.

#### How to use:
* Clone the repo.
* Start Docker.
* Run rabbitMQ in Docker:<code>docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management</code>
* Run QuotaRequestConsumer.main.
* Run QuotaRequestProducer.main.