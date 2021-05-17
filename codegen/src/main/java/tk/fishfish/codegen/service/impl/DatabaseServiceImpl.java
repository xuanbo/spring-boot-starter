package tk.fishfish.codegen.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.fishfish.codegen.condition.DatabaseCondition;
import tk.fishfish.codegen.config.datasource.DataSourceHub;
import tk.fishfish.codegen.dto.Table;
import tk.fishfish.codegen.entity.Database;
import tk.fishfish.codegen.service.DatabaseService;
import tk.fishfish.execption.BizException;
import tk.fishfish.mybatis.service.impl.BaseServiceImpl;
import tk.fishfish.rest.model.ApiResult;

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
    public void afterInsert(Database database) {
        dataSourceHub.put(database.getId(), dataSourceHub.createDataSource(database));
    }

    @Override
    public void afterUpdate(Database database) {
        dataSourceHub.put(database.getId(), dataSourceHub.createDataSource(database));
    }

    @Override
    public ApiResult<Void> ping(Database database) {
        DataSource dataSource = dataSourceHub.createDataSource(database);
        try (Connection con = dataSource.getConnection()) {
            return ApiResult.ok(null);
        } catch (SQLException e) {
            return ApiResult.fail(400, e.getMessage());
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
            rs = databaseMetaData.getTables(getCatalog(con, condition), getSchema(con, condition), "%", new String[]{"TABLE"});
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
            rs = databaseMetaData.getColumns(getCatalog(con, condition), getSchema(con, condition), condition.getName(), "%");
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

    private String getCatalog(Connection con, DatabaseCondition.TableQuery condition) throws SQLException {
        if (StringUtils.isEmpty(condition.getCatalog())) {
            return con.getCatalog();
        }
        return condition.getCatalog();
    }

    private String getSchema(Connection con, DatabaseCondition.TableQuery condition) throws SQLException {
        if (StringUtils.isEmpty(condition.getSchema())) {
            return con.getSchema();
        }
        return condition.getSchema();
    }

}
