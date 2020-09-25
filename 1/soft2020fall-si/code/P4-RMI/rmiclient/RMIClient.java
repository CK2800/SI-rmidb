package rmiclient;
/**
 * This is a simple client program that uses methods as a service from another, server program.
 * 
 * There are two services provided by the server and consumed by this client:
 * - calculation
 * - date/time info
 *
 * The client gets input from the console: two operands/numbers and the operator/character
 * Then the client builds a stub, which searches for a service and connects with the server for getting it
 * Finally, the client prints out the results on the console
 * 
 * @author Dora Di
 */
import java.rmi.*;
import java.util.Scanner;
import rmiserver.*;

public class RMIClient
{
    private static int a;
    private static int b;
    private static char op;
    public static final String OPS = "+-*/%";
    
    public static void getInput()
    {
            Scanner inp = new Scanner(System.in);
            System.out.println("Enter two integer numbers: ");
            a = inp.nextInt();
            b = inp.nextInt();
            do 
            {
                System.out.println("Enter operator: ");
                op = inp.next().charAt(0);
            }
            while (OPS.indexOf(op) == -1); 
    }
    
    public static void getService() throws Exception 
    {
        double result;
        java.util.Date today;

            // Lookup in the registry for the service interface you know by name
            RMIInterface obj = (RMIInterface) Naming.lookup("//localhost/Compute");

            // Send requests, get responses
            result = obj.calculate(a, b, op);
            today = obj.getDate();

            printout(result, today);  
    }
    
    public static void printout(double result, java.util.Date today)
    {
        // Print the results on the Client console
        System.out.println("Server says: result = " + result);
        System.out.println("Server says: today is " + today);
    }        
        
    
    public static void main(String[] args)
    {
        try
        {
            getInput();
            getService();  
        }
        catch (Exception e)
        {
          System.out.println("Exception: " + e);
        } 
    }
}
