/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p5;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Dora Di
 */
// View/Tools/Database/+
@Entity
@Table(name = "CUSTOMER")
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
    @NamedQuery(name = "Customer.findByAccnum", query = "SELECT c FROM Customer c WHERE c.accnum = :accnum"),
    @NamedQuery(name = "Customer.findByName", query = "SELECT c FROM Customer c WHERE c.name = :name"),
    @NamedQuery(name = "Customer.findByAmount", query = "SELECT c FROM Customer c WHERE c.amount = :amount")})
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ACCNUM")
    private Integer accnum;
    @Column(name = "NAME")
    private String name;
    @Column(name = "AMOUNT")
    private Integer amount;

    public Customer() {
    }

    public Customer(Integer accnum) {
        this.accnum = accnum;
    }

    public Integer getAccnum() {
        return accnum;
    }

    public void setAccnum(Integer accnum) {
        this.accnum = accnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accnum != null ? accnum.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.accnum == null && other.accnum != null) || (this.accnum != null && !this.accnum.equals(other.accnum))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "p5.Customer[ accnum=" + accnum + " ]";
    }
    
}
