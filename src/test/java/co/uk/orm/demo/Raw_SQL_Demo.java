package co.uk.orm.demo;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

public class Raw_SQL_Demo {

    @ClassRule
    public static CreateDatabaseConnectionRule toConnectToDatabase = new CreateDatabaseConnectionRule();

    private CustomerRepository customerRepository;

    @Before
    public void setup() {
        customerRepository = toConnectToDatabase.getHandle().attach(CustomerRepository.class);
    }

    @Test
    public void should_insert_test_data_using_raw_sql() {
        customerRepository.insertIntoCustomer("Ilyas", "Patel", "sql_demo@mycompany.com");
    }
}
