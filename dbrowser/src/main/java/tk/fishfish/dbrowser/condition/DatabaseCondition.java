package tk.fishfish.dbrowser.condition;

import lombok.Data;
import tk.fishfish.dbrowser.entity.enums.DriverType;
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

    public interface Schema {
    }

    public interface Table {
    }

    @Data
    public static class Meta {

        @NotBlank(groups = {Schema.class, Table.class})
        private String schema;

        @NotBlank(groups = Table.class)
        private String table;

    }

    @Data
    public static class Execute {

        private String schema;

        @NotBlank
        private String sql;

    }

}
