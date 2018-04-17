package co.uk.orm.demo;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import javax.sql.DataSource;

public class CreateDatabaseConnectionRule implements TestRule {

    private DBI dbi;
    private Handle h;

    private String driverClassName;
    private String url;
    private String username;
    private String password;

    public CreateDatabaseConnectionRule() {
        this.driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        this.url = "";
        this.username = "";
        this.password = "";
    }

    public Statement apply(final Statement base, final Description description) {

        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                createConnection();
                try {
                    base.evaluate(); // run underlying statement
                } finally {
                    closeConnection();
                }
            }
        };
    }

    private void createConnection() {

        DataSourceFactory dataSourceFactory =
                new DataSourceFactory(this.driverClassName, this.url, this.username, this.password);
        DataSource dataSource = dataSourceFactory.getDataSource();

        h = new DBI(dataSource).open();
    }

    public Handle getHandle() {
        return h;
    }

    private void closeConnection() {
        h.close();
    }
}
