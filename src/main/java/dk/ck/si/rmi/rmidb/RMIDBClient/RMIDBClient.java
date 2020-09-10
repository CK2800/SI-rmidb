package dk.ck.si.rmi.rmidb.RMIDBClient;
/**
 *
 *
 */
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.rmi.Naming;
import java.util.List;

import dk.ck.si.rmi.rmidb.RMIDBServer.BankInterface;
import dk.ck.si.rmi.rmidb.RMIDBServer.Customer;


public class RMIDBClient
{
    public static void main(String args[])throws Exception
    {
        // name =  rmi:// + ServerIP +  /EngineName;
        String remoteEngine = "rmi://localhost/BankServices";

        // Create local stub, lookup in the registry searching for the remote engine - the interface with the methods we want to use remotely
        BankInterface obj = (BankInterface) Naming.lookup(remoteEngine);

        System.out.println(obj.getTotalCustomers() + " customers in bank.");
        System.out.println("Customers added: " + obj.addJsonCustomers(new File("./src/main/resources/customers.json")));
        System.out.println(obj.getTotalCustomers() + " customers in bank.");

        //obj.addJsonCustomer("{\"accnum\": 5677, \"name\": \"Anders And\", \"amount\": 565660.65}");

        List<Customer> list=obj.getMillionaires();
        for(Customer c:list)
        {
            System.out.println(c.getAccnum()+ " " + c.getName() + " " + c.getAmount());
        }

    }

}
