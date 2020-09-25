/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p5.rmiclientdb;
/**
 *
 * @author Dora Di
 */

import java.util.*;  
import java.rmi.*; 
import p5.*;

public class RMIClientDB 
{
    public static void main(String args[])throws Exception
    {  
            // name =  rmi:// + ServerIP +  /EngineName;
            String remoteEngine = "rmi://localhost/Compute";
            
            // Create local stub, lookup in the registry searching for the remote engine - the interface with the methods we want to use remotely
            BankInterface obj = (BankInterface) Naming.lookup(remoteEngine);
            


        List<Customer> list=obj.getCustomers();  
        for(Customer c:list)
        {  
            System.out.println(c.getAccnum()+ " " + c.getName() + " " + c.getAmount());  
        }  
    }
  
} 
