package rmiserver;
/**
 *
 * @author Dora Di
 */
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIInterface extends Remote
{
  // Simple arithmetics
  public double calculate(int a, int b, char c) throws RemoteException;

  // Get current date
  public java.util.Date getDate() throws RemoteException;

}

