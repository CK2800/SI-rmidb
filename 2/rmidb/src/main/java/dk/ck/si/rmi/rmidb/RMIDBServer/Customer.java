package dk.ck.si.rmi.rmidb.RMIDBServer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity

// here we need to implement Serializable because RMI uses serialization to transmit data.

public class Customer implements Serializable
{
    @Id
    private Long accnum;
    @NonNull
    private String name;
    @NonNull
    private Double amount;
}

@JacksonXmlRootElement(localName = "Customers")
class Customers
{
    @JacksonXmlProperty(localName="Customer")
    @JacksonXmlElementWrapper(useWrapping = false)
    public List<Customer> customers;
}


