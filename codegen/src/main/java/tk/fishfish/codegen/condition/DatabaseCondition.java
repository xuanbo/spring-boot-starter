package tk.fishfish.codegen.condition;

import lombok.Data;
import tk.fishfish.codegen.entity.enums.DriverType;
import tk.fishfish.mybatis.condition.annotation.Eq;
import tk.fishfish.mybatis.condition.annotation.Like;

import javax.validation.constraints.NotBlank;

/**
 * 数据库条件
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Data
public class DatabaseCondition {

    @Like(policy = Like.Policy.ALL)
    private String name;

    @Eq
    private DriverType type;

    @Data
    public static class TableQuery {

        @NotBlank(groups = {Tables.class, Table.class})
        private String id;

        private String catalog;

        private String schema;

        @NotBlank(groups = {Table.class})
        private String name;

    }

    public interface Tables {
    }

    public interface Table {
    }

}
