package dk.ck.si.rmi.rmidb.RMIDBServer;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

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
