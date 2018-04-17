package co.uk.orm.demo;

import co.uk.orm.demo.d.loader.service.GroovyScriptLoaderImpl;
import co.uk.orm.demo.d.loader.service.Loader;
import co.uk.orm.demo.domain.Customer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;

public class Hibernate_Demo {

    private Loader orm;
    private EntityManager entityManager;

    @Before
    public void setup() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "classpath:/applicationContext-d-loader.xml"
        );

        EntityManagerFactory entityManagerFactory = (EntityManagerFactory) applicationContext.getBean("myEmf");
        entityManager = entityManagerFactory.createEntityManager();
        orm = new GroovyScriptLoaderImpl(entityManager);
    }

    @Test
    public void should_insert_test_data_using_hibernate() {

        // Given
        List<String> testData = singletonList("src/test/resources/customers.groovy");

        // When
        Map<String, Object> data = orm.persist(testData);

        // Then
        Customer c1 = (Customer) data.get("customer_1");
        Customer c2 = (Customer) data.get("customer_2");
        Customer c3 = (Customer) data.get("customer_3");
        Customer c4 = (Customer) data.get("customer_4");
        Customer c5 = (Customer) data.get("customer_5");

        System.out.println(c1.id + " - " + c1.firstname + " - " + c1.lastname + " - " + c1.email);
        System.out.println(c2.id + " - " + c2.firstname + " - " + c2.lastname + " - " + c2.email);
        System.out.println(c3.id + " - " + c3.firstname + " - " + c3.lastname + " - " + c3.email);
        System.out.println(c4.id + " - " + c4.firstname + " - " + c4.lastname + " - " + c4.email);
        System.out.println(c5.id + " - " + c5.firstname + " - " + c5.lastname + " - " + c5.email);
    }

}
