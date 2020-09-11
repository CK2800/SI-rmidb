
package dk.ck.si.rmi.rmidb.RMIDBServer;

/**
 *
 * @author Dora Di
 */
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.beans.XMLDecoder;
import java.io.*;
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
        /* PSEUDO
        1. Read file and collect it as a string.
        2. Parse the string with json mapper and get list of customers.
        3. send list of customers to method to persist them.
        4. return how many was persisted.
         */
        int result = 0;
        Connection connection = null;
        try
        {
            //String content = parseFileToString(xmlFile);
            List<Customer> customers = mapXmlCustomers(xmlFile);
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
            result = storeCustomers(customers, connection);
        }
        catch(Exception e)
        {
            System.out.println("XML customers not stored. " + e.getMessage());
        }
        finally
        {
            try
            {
                connection.close();
            }
            catch(Exception e)
            {
                if (connection == null)
                    System.out.println("Connection was never established.");
                else
                    System.out.println("Connection was not closed.");
            }
        }
        return result;
    }

    public int addJsonCustomers(File jsonFile) throws RemoteException
    {
        /* PSEUDO
        1. Read file and collect it as a string.
        2. Parse the string with json mapper and get list of customers.
        3. send list of customers to method to persist them.
        4. return how many was persisted.
         */
        int result = 0;
        Connection connection = null;
        try {
            String content = parseFileToString(jsonFile);
            List<Customer> customers = mapJsonCustomers(content);
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
            result = storeCustomers(customers, connection);
        }
        catch (Exception e)
        {
            System.out.println("JSON customers not stored. " + e.getMessage());
        }
        finally
        {
            try
            {
                connection.close();
            }
            catch(Exception e)
            {
                if (connection == null)
                    System.out.println("Connection was never established.");
                else
                    System.out.println("Connection was not closed.");
            }
        }
        return result;
    }

    private String parseFileToString(File file) throws FileNotFoundException, IOException
    {
        StringBuilder content = new StringBuilder();
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while((line = bufferedReader.readLine()) != null)
            content.append(line);

        return content.toString();
    }

    private List<Customer> mapXmlCustomers(File xmlFile) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(Customer.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Customer[] customers = ((Customer[]) unmarshaller.unmarshal(xmlFile));
        return Arrays.asList(customers);

    }


    private List<Customer> mapJsonCustomers(String json) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        List<Customer> customers = new ArrayList<Customer>();
        return Arrays.asList(mapper.readValue(json, Customer[].class));
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

    private int storeCustomers(List<Customer> customers, Connection connection)
    {
        int count = 0;
        for(Customer customer : customers)
        {
            try
            {
                count++;
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Customer(name, amount) VALUES (?,?)");
                preparedStatement.setString(1, customer.getName());
                preparedStatement.setDouble(2, customer.getAmount());
                preparedStatement.executeUpdate();
            }
            catch(Exception e)
            {
                count--;
                System.out.println("Unable to add customer: " + customer.toString() + ", " + e.getMessage());
            }
        }
        return count;
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



