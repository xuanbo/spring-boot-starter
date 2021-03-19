package tk.fishfish.codegen.condition;

import lombok.Data;
import tk.fishfish.codegen.dto.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 模板条件
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Data
public class TemplateCondition {

    @NotBlank
    private String id;

    /**
     * 包
     */
    @NotBlank
    private String pkg;

    /**
     * 表前缀
     */
    private String prefix;

    /**
     * 作者
     */
    private String author;

    @NotEmpty
    private List<Table> tables;

}
