package tk.fishfish.dbrowser.service;

import tk.fishfish.dbrowser.condition.DatabaseCondition;
import tk.fishfish.dbrowser.database.SqlResult;
import tk.fishfish.dbrowser.database.Table;
import tk.fishfish.dbrowser.entity.Database;
import tk.fishfish.mybatis.service.BaseService;
import tk.fishfish.rest.model.ApiResult;

import java.util.List;

/**
 * 数据库
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
public interface DatabaseService extends BaseService<Database> {

    /**
     * 测试数据库连接
     *
     * @param database 数据库
     * @return 连接异常信息
     */
    ApiResult<Void> ping(Database database);

    List<String> schemas(String id);

    List<String> tables(String id, DatabaseCondition.Meta condition);

    Table table(String id, DatabaseCondition.Meta condition);

    SqlResult execute(String id, DatabaseCondition.Execute condition);

    void load();

}
