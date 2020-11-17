package rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static rabbitmq.QuotaRequestConsumer.*;

public class QuotaRequestProducer
{
    private Connection connection;
    private String exchangeName;


    public QuotaRequestProducer(String host, int port, String exchangeName) throws IOException, TimeoutException
    {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        connection = factory.newConnection();
        this.exchangeName = exchangeName;

    }
    private void sendQuotaRequest() throws IOException
    {
        try (Channel channel = connection.createChannel())
        {
            String body = "quoteRequest";
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT, false);
            // no specific queue needed since exchange is fanout.
            channel.basicPublish(exchangeName, "", null, body.getBytes("UTF-8"));
            System.out.println("Sent quota request: " + body);

        }
        catch (TimeoutException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        awaitResponses();
    }

    private void awaitResponses() throws IOException
    {
        System.out.println("Awaiting responses...");

        Channel channel = connection.createChannel();
        DeliverCallback callback = (s, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("consumer received: " + message);
        };


        // start consuming from response exchange.
        channel.exchangeDeclare(RESPONSE_EXCHANGE_NAME, BuiltinExchangeType.DIRECT, false);

        // Declare the queue for responses from the banks, bind it to the exchange and start consuming.
        channel.queueDeclare(RESPONSE_QUEUE_NAME, true, false, false, null);
        channel.queueBind(RESPONSE_QUEUE_NAME, RESPONSE_EXCHANGE_NAME, RESPONSE_QUEUE_NAME);

        int i = 0;
        GetResponse response = null;
        while(response == null && i < 4)
        {
            response = channel.basicGet(RESPONSE_QUEUE_NAME, true);
            if (response != null)
            {
                i++;
                String message = new String(response.getBody(), "UTF-8");
                System.out.println(message);
            }
            response = null;
        }
        System.out.println("Producer received " + i + " quotes.");

        //channel.basicConsume(RESPONSE_QUEUE_NAME, true, callback, consumerTag -> {});
    }

    public static void main(String[] args) throws IOException, TimeoutException
    {
        QuotaRequestProducer producer = new QuotaRequestProducer("localhost", 5672, QuotaRequestConsumer.REQUEST_EXCHANGE_NAME);
        producer.sendQuotaRequest();
    }
}
