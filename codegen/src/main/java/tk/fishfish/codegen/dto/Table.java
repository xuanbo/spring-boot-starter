package tk.fishfish.codegen.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 表
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Table {

    private String name;

    private String catalog;

    private String schema;

    private String remark;

    private List<Column> columns;

    /**
     * 包
     */
    private String pkg;

    /**
     * 表前缀
     */
    private String prefix;

    /**
     * 作者
     */
    private String author;

    /**
     * 实体名称
     */
    private String entity;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Column {

        private String name;

        private String type;

        private String remark;

    }

}
