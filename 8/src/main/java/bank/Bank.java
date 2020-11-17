package bank;

import com.rabbitmq.client.DeliverCallback;


public class Bank
{
    private int quota;
    private String name;
    public int getQuota(){return quota;}
    public String getName(){return name;}

    public Bank(String name, int quota)
    {
        this.name = name;
        this.quota = quota;
    }
}
