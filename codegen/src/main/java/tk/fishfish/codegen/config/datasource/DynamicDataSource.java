package tk.fishfish.codegen.config.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.AbstractDataSource;

import javax.sql.DataSource;
import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 动态数据源
 *
 * @author 奔波儿灞
 * @since 1.5.0
 */
@Slf4j
public class DynamicDataSource extends AbstractDataSource implements DataSourceHub {

    private final ConcurrentMap<String, DataSource> dataSources;

    private DataSource defaultDataSource;

    public DynamicDataSource() {
        dataSources = new ConcurrentHashMap<>();
    }

    public void setDefaultTargetDataSource(DataSource defaultTargetDataSource) {
        this.defaultDataSource = defaultTargetDataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return determineTargetDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return determineTargetDataSource().getConnection(username, password);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface.isInstance(this)) {
            return (T) this;
        }
        return determineTargetDataSource().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return (iface.isInstance(this) || determineTargetDataSource().isWrapperFor(iface));
    }

    private DataSource determineTargetDataSource() {
        String lookupKey = DataSourceContextHolder.current();
        DataSource dataSource = Optional.ofNullable(lookupKey)
                .map(dataSources::get)
                .orElse(defaultDataSource);
        if (dataSource == null) {
            throw new IllegalStateException("Cannot determine DataSource for lookup key [" + lookupKey + "]");
        }
        return dataSource;
    }

    @Override
    public void put(String id, DataSource dataSource) {
        Optional.ofNullable(dataSources.put(id, dataSource))
                .ifPresent(e -> {
                    if (e instanceof Closeable) {
                        try {
                            ((Closeable) e).close();
                        } catch (IOException ioException) {
                            // ignore
                        }
                    }
                });
    }

    @Override
    public DataSource get(String id) {
        return dataSources.get(id);
    }

    @Override
    public void remove(String id) {
        Optional.ofNullable(dataSources.remove(id))
                .ifPresent(e -> {
                    if (e instanceof Closeable) {
                        try {
                            ((Closeable) e).close();
                        } catch (IOException ioException) {
                            // ignore
                        }
                    }
                });
    }

}
