package dk.ck.si.rmi.rmidb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Tell spring where to scan for rest controller (BankImplementation)
@SpringBootApplication(scanBasePackages = {"dk.ck.si.rmi.rmidb.RMIDBServer"})
public class RmidbApplication {

    public static void main(String[] args)
    {

        SpringApplication.run(RmidbApplication.class, args);
    }
}
