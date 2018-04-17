package co.uk.orm.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity(name = "Customer")
@Table(name = "Customer")
public class Customer implements Serializable {

    @Id
    @GeneratedValue
    public int id;
    public String firstname;
    public String lastname;
    public String email;

}
