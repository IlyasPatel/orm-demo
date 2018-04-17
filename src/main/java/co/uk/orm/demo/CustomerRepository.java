package co.uk.orm.demo;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

import java.util.List;

public interface CustomerRepository {

    @SqlUpdate("INSERT INTO customer(firstname, lastname, email) VALUES (:firstname, :lastname, :email) ")
    void insertIntoCustomer(@Bind("firstname") String firstname, @Bind("lastname") String lastname, @Bind("email") String email);

    void close();
}
