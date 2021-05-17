package tk.fishfish.dbrowser.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.fishfish.dbrowser.condition.DatabaseCondition;
import tk.fishfish.dbrowser.database.SqlResult;
import tk.fishfish.dbrowser.database.Table;
import tk.fishfish.dbrowser.entity.Database;
import tk.fishfish.dbrowser.service.DatabaseService;
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
@RequestMapping("/v1/database")
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

    @GetMapping("/{id}/schemas")
    public List<String> schemas(@PathVariable String id) {
        return databaseService.schemas(id);
    }

    @PostMapping("/{id}/tables")
    public List<String> tables(
            @PathVariable String id,
            @Validated(DatabaseCondition.Schema.class) @RequestBody DatabaseCondition.Meta condition
    ) {
        return databaseService.tables(id, condition);
    }

    @PostMapping("/{id}/table")
    public Table table(
            @PathVariable String id,
            @Validated(DatabaseCondition.Table.class) @RequestBody DatabaseCondition.Meta condition
    ) {
        return databaseService.table(id, condition);
    }

    @PostMapping("/{id}/execute")
    public SqlResult execute(
            @PathVariable String id,
            @Validated(DatabaseCondition.Table.class) @RequestBody DatabaseCondition.Execute condition
    ) {
        return databaseService.execute(id, condition);
    }

}
