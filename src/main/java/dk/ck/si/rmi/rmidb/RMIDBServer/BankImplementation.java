
package dk.ck.si.rmi.rmidb.RMIDBServer;

/**
 *
 * @author Dora Di
 */
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class BankImplementation extends UnicastRemoteObject implements BankInterface
{
    //public static String url = "jdbc:h2:mem:Bank";
    public static String url = "jdbc:h2:file:./src/main/resources/db/data/bank";

    //public static String url = "jdbc:h2:file:/users/tdi/ideaprojects/p5-rmi-db-Server/src/main/resources/db/bank";
    public static String user = "sa";
    public static String password = "";
    public static String driver = "org.h2.Driver";

    BankImplementation()throws RemoteException{}

    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name)
    {
        return String.format("Hello %s!", name);
    }

    public int addXmlCustomers(File xmlFile) throws RemoteException
    {
        // TBD
        return 0;
    }

    public int addJsonCustomers(File jsonFile) throws RemoteException
    {
        try
        {
            StringBuilder customers = new StringBuilder();
            FileReader fileReader = new FileReader(jsonFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String customer;
            while((customer = bufferedReader.readLine()) != null)
                customers.append(customer);

            bufferedReader.close();
            fileReader.close();

            return addJsonCustomer(customers.toString());
        }
        catch(Exception e)
        {
            System.out.println("File could not be read: " + e.getMessage());
        }
        return 0;
    }

    public int getTotalCustomers() throws RemoteException
    {
        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, user, password);
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT COUNT(*) as ROW_COUNT FROM Customer");
            int result;
            if (rs.next())
                result = rs.getInt("ROW_COUNT");
            else
                result = 0;
            con.close();
            return result;
        }
        catch(Exception e)
        {
            System.out.println("BURN : " + e.getMessage());
        }
        return 0;
    }

    private int addJsonCustomer(String json) throws RemoteException
    {
        ObjectMapper mapper = new ObjectMapper();
        List<Customer> customers = new ArrayList<Customer>();
        try
        {
            customers = Arrays.asList(mapper.readValue(json, Customer[].class));
            Class.forName(driver);
            Connection con=DriverManager.getConnection(url, user, password);

            for(Customer customer : customers) {
                //PreparedStatement ps = con.prepareStatement("INSERT INTO Customer(accnum, name, amount) VALUES (?,?,?)");
                PreparedStatement ps = con.prepareStatement("INSERT INTO Customer(name, amount) VALUES (?,?)");

                //ps.setLong(1, customer.getAccnum());
                ps.setString(1, customer.getName());
                ps.setDouble(2, customer.getAmount());
                ps.executeUpdate();
            }
            con.close();

        }
        catch(Exception e)
        {
            System.out.println("Mapping failed: " + e.getMessage());
        }
        return customers.size();
    }

    @GetMapping("/bank")
    public List<Customer> getMillionaires()
    {

        List<Customer> list=new ArrayList<Customer>();
        try
        {
            Class.forName(driver);
            Connection con=DriverManager.getConnection(url, user, password);
            PreparedStatement ps=con.prepareStatement("select * from Customer where amount >= 100000;");
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
                Customer c=new Customer();
                c.setAccnum(rs.getLong(1));
                c.setName(rs.getString(2));
                c.setAmount(rs.getDouble(3));
                System.out.println(c);
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



