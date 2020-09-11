package dk.ck.si.rmi.rmidb.RMIDBServer;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface BankInterface extends Remote
{
    List<Customer> getMillionaires() throws RemoteException;
    int addCustomers(File file) throws RemoteException, Exception;
    int getTotalCustomers() throws RemoteException;
    // @Query(value = "SELECT name FROM Customer  WHERE amount > 1000000")
    // List<Customer> findAllMillions();
    // List<Customer> findAllByName(String name);
}
