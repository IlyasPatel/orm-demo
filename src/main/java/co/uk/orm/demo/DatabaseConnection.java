package co.uk.orm.demo;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import javax.sql.DataSource;

public class DatabaseConnection {

    private DBI dbi;
    private Handle handle;

    private String driverClassName;
    private String url;
    private String username;
    private String password;

    public DatabaseConnection(String driverClassName, String url, String username, String password) {
        this.driverClassName = driverClassName;
        this.url = url;
        this.username = username;
        this.password = password;

        createConnection();
    }

    private void createConnection() {

        DataSourceFactory dataSourceFactory =
                new DataSourceFactory(this.driverClassName, this.url, this.username, this.password);
        DataSource dataSource = dataSourceFactory.getDataSource();

        handle = new DBI(dataSource).open();
    }

    public Handle getHandle() {
        return handle;
    }

    private void closeConnection() {
        handle.close();
    }

}
