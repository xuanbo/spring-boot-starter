package tk.fishfish.codegen.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.fishfish.codegen.condition.DatabaseCondition;
import tk.fishfish.codegen.dto.Table;
import tk.fishfish.codegen.entity.Database;
import tk.fishfish.codegen.service.DatabaseService;
import tk.fishfish.mybatis.controller.BaseController;
import tk.fishfish.mybatis.domain.Page;
import tk.fishfish.mybatis.domain.Query;
import tk.fishfish.rest.model.ApiResult;

import java.util.List;

/**
 * 数据库
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v0/database")
public class DatabaseController extends BaseController<Database> {

    private final DatabaseService databaseService;

    @PostMapping("/page")
    public Page<Database> page(@RequestBody Query<DatabaseCondition> query) {
        return databaseService.page(query.getCondition(), query.getPage());
    }

    @PostMapping("/list")
    public List<Database> list(@RequestBody DatabaseCondition condition) {
        return databaseService.query(condition);
    }

    @PostMapping("/ping")
    public ApiResult<Void> ping(@RequestBody Database database) {
        return databaseService.ping(database);
    }

    @PostMapping("/tables")
    public List<Table> tables(@Validated(DatabaseCondition.Tables.class) @RequestBody DatabaseCondition.TableQuery condition) {
        return databaseService.tables(condition);
    }

    @PostMapping("/table")
    public Table table(@Validated(DatabaseCondition.Table.class) @RequestBody DatabaseCondition.TableQuery condition) {
        return databaseService.table(condition);
    }

}
