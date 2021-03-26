package tk.fishfish.codegen.service;

import tk.fishfish.codegen.condition.DatabaseCondition;
import tk.fishfish.codegen.dto.Table;
import tk.fishfish.codegen.entity.Database;
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

    /**
     * 查询表
     *
     * @param condition 条件
     * @return 表
     */
    List<Table> tables(DatabaseCondition.TableQuery condition);

    /**
     * 查询表结构
     *
     * @param condition 条件
     * @return 表结构
     */
    Table table(DatabaseCondition.TableQuery condition);

}
