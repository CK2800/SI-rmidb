/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p5;

/**
 *
 * @author Dora Di
 */
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface BankInterface extends Remote
{
    public List<Customer> getCustomers()throws RemoteException;
}
