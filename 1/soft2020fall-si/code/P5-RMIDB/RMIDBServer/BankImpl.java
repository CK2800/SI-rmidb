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
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

class BankImpl extends UnicastRemoteObject implements BankInterface
{  
    BankImpl()throws RemoteException{}  
  
    public List<Customer> getCustomers()
    {  
        List<Customer> list=new ArrayList<Customer>();  
        try
        {  
            Class.forName("org.apache.derby.jdbc.ClientDriver"); 
            Connection con=DriverManager.getConnection("jdbc:derby://localhost:1527/Bank","APP","root");              
            PreparedStatement ps=con.prepareStatement("select * from customer");  
            ResultSet rs=ps.executeQuery();  

            while(rs.next())
            {  
                Customer c=new Customer();  
                c.setAccnum(rs.getInt(1));  
                c.setName(rs.getString(2));  
                c.setAmount(rs.getInt(3));  
                list.add(c);  
            }  
            con.close();  
        }
        catch(Exception e)
        {
            System.out.println(e);
        }  
        return list;  
    }  
}  



