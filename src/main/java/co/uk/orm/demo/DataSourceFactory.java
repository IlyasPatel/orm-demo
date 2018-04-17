package co.uk.orm.demo;

import com.google.common.base.Throwables;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceFactory {

    private final DataSource dataSource;

    public DataSourceFactory(String driverClassName, String url, String username, String password) {
        this.dataSource = instantiateDataSource(driverClassName, url, username, password);
    }

    private BasicDataSource instantiateDataSource(String driverClassName, String url, String username, String password) {
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            throw Throwables.propagate(e);
        }

        BasicDataSource basicDataSource = new BasicDataSource();

        basicDataSource.setDriverClassName(driverClassName);

        basicDataSource.setUrl(url);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);
        basicDataSource.setInitialSize(1);

        return basicDataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
