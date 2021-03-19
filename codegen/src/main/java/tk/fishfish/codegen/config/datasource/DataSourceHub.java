package tk.fishfish.codegen.config.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import tk.fishfish.codegen.entity.Database;

import javax.sql.DataSource;

/**
 * 数据源管理
 *
 * @author 奔波儿灞
 * @since 1.5.0
 */
public interface DataSourceHub {

    /**
     * 新增或更新数据源
     *
     * @param id         主键
     * @param dataSource 数据源对象
     */
    void put(String id, DataSource dataSource);

    /**
     * 根据主键获取数据源
     *
     * @param id 主键
     * @return 数据源对象
     */
    DataSource get(String id);

    /**
     * 移除数据源
     *
     * @param id 主键
     */
    void remove(String id);

    default DataSource createDataSource(Database database) {
        return DataSourceBuilder.create(DataSourceHub.class.getClassLoader()).type(HikariDataSource.class)
                .url(database.getUrl())
                .username(database.getUsername()).password(database.getPassword())
                .build();
    }

}
