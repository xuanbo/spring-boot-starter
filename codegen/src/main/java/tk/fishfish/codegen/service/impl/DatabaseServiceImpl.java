package tk.fishfish.codegen.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.fishfish.codegen.condition.DatabaseCondition;
import tk.fishfish.codegen.config.datasource.DataSourceHub;
import tk.fishfish.codegen.dto.Table;
import tk.fishfish.codegen.entity.Database;
import tk.fishfish.codegen.service.DatabaseService;
import tk.fishfish.mybatis.service.impl.BaseServiceImpl;
import tk.fishfish.rest.execption.BizException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 数据库
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Service
@RequiredArgsConstructor
public class DatabaseServiceImpl extends BaseServiceImpl<Database> implements DatabaseService {

    private final DataSourceHub dataSourceHub;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        dataSourceHub.remove(id);
        super.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(List<String> ids) {
        ids.forEach(dataSourceHub::remove);
        super.deleteByIds(ids);
    }

    @Override
    protected void afterInsert(Database database) {
        dataSourceHub.put(database.getId(), dataSourceHub.createDataSource(database));
    }

    @Override
    protected void afterUpdate(Database database) {
        dataSourceHub.put(database.getId(), dataSourceHub.createDataSource(database));
    }

    @Override
    public String ping(Database database) {
        DataSource dataSource = dataSourceHub.createDataSource(database);
        try (Connection con = dataSource.getConnection()) {
            return null;
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    @Override
    public List<Table> tables(DatabaseCondition.TableQuery condition) {
        DataSource dataSource = Optional.ofNullable(dataSourceHub.get(condition.getId()))
                .orElseThrow(() -> BizException.of(404, "数据源不存在"));
        Connection con = null;
        ResultSet rs = null;
        List<Table> tables = new ArrayList<>();
        try {
            con = dataSource.getConnection();
            DatabaseMetaData databaseMetaData = con.getMetaData();
            rs = databaseMetaData.getTables(condition.getCatalog(), condition.getSchema(), "%", new String[]{"TABLE"});
            while (rs.next()) {
                tables.add(
                        Table.builder()
                                .name(rs.getString("TABLE_NAME"))
                                .catalog(rs.getString("TABLE_CAT"))
                                .schema(rs.getString("TABLE_SCHEM"))
                                .remark(rs.getString("REMARKS"))
                                .build()
                );
            }
        } catch (SQLException e) {
            throw BizException.of(500, "查询表错误", e);
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeConnection(con);
        }
        return tables;
    }

    @Override
    public Table table(DatabaseCondition.TableQuery condition) {
        DataSource dataSource = Optional.ofNullable(dataSourceHub.get(condition.getId()))
                .orElseThrow(() -> BizException.of(404, "数据源不存在"));
        Connection con = null;
        ResultSet rs = null;
        List<Table.Column> columns = new ArrayList<>();
        try {
            con = dataSource.getConnection();
            DatabaseMetaData databaseMetaData = con.getMetaData();
            rs = databaseMetaData.getColumns(condition.getCatalog(), condition.getSchema(), condition.getName(), "%");
            while (rs.next()) {
                columns.add(
                        Table.Column.builder()
                                .name(rs.getString("COLUMN_NAME"))
                                .type(rs.getString("TYPE_NAME"))
                                .remark(rs.getString("REMARKS"))
                                .build()
                );
            }
        } catch (SQLException e) {
            throw BizException.of(500, "查询表错误", e);
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeConnection(con);
        }
        return Table.builder()
                .catalog(condition.getCatalog())
                .schema(condition.getSchema())
                .name(condition.getName())
                .columns(columns)
                .build();
    }

}
