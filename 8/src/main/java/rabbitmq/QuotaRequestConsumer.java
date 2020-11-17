package rabbitmq;

import bank.Bank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.*;

/**
 * Consumes a request for quota.
 * Produces a response with the quota.
 */
public class QuotaRequestConsumer
{
    public static final String REQUEST_EXCHANGE_NAME = "bank_quota_requests";
    public static final String RESPONSE_EXCHANGE_NAME = "bank_quota_responses";
    public static final String REQUEST_QUEUE_NAME_PREFIX = "requests_for_";
    public static final String RESPONSE_QUEUE_NAME = "response_from_bank";

    public static int NO_OF_QUEUES = 0;

    protected Connection connection;
    private Bank bank;

    public QuotaRequestConsumer(String host, int port, Bank bank) throws Exception
    {
        this.bank = bank;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        connection = factory.newConnection();
        Channel channel = connection.createChannel();

        System.out.println("Quota request consumer running for bank: " + bank.getName());

        DeliverCallback callback = ((consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(bank.getName() + " received: " + message);
            System.out.println(bank.getName() + " will return the quote of: " + bank.getQuota() + " on queue: " +  RESPONSE_QUEUE_NAME);
            sendQuotaResponse();
        });

        // Declare exchange for requests.
        channel.exchangeDeclare(REQUEST_EXCHANGE_NAME, BuiltinExchangeType.FANOUT, false);
        // Declare the queue for requests to the bank, bind it to the exchange and start consuming.
        String requestQueueName = REQUEST_QUEUE_NAME_PREFIX + getQueueNamePostfixFor(bank);
        channel.queueDeclare(requestQueueName, true, false, false, null);
        channel.queueBind(requestQueueName, REQUEST_EXCHANGE_NAME, "");
        channel.basicConsume(requestQueueName, true, callback, consumerTag -> {});
    }

    private String getQueueNamePostfixFor(Bank bank)
    {
        return bank.getName().replace(' ', '_');
    }

    private void sendQuotaResponse()
    {
        try (Channel channel = connection.createChannel())
        {
            String body = bank.getName() + ": quote: " + bank.getQuota();

            channel.exchangeDeclare(RESPONSE_EXCHANGE_NAME, BuiltinExchangeType.DIRECT, false);
            channel.queueDeclare(RESPONSE_QUEUE_NAME, true, false, false, null);
            // Bind queue (with name, to exchange, with routing key).
            channel.queueBind(RESPONSE_QUEUE_NAME, RESPONSE_EXCHANGE_NAME, RESPONSE_QUEUE_NAME);
            // Publish (on channel, with routing key, ...)
            channel.basicPublish(RESPONSE_EXCHANGE_NAME, RESPONSE_QUEUE_NAME, null, body.getBytes("UTF8"));
            System.out.println("Sent message: " + body);
        }
        catch (TimeoutException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception
    {
        int port = 5672;
        String host = "localhost";
        // Declare some QuotaRequestConsumers.
        List<QuotaRequestConsumer> consumers = new ArrayList<>();
        consumers.add(new QuotaRequestConsumer(host, port, new Bank("Spar op", 5)));
        consumers.add(new QuotaRequestConsumer(host, port, new Bank("Spar ned", 10)));
        consumers.add(new QuotaRequestConsumer(host, port, new Bank("Klør op", 6)));
        consumers.add(new QuotaRequestConsumer(host, port, new Bank("Klør ned", 2)));
    }

}
