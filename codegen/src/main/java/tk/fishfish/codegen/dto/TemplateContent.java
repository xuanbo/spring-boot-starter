package tk.fishfish.codegen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 模板内容
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateContent {

    /**
     * 模板名称
     */
    private String name;

    /**
     * 模板内容
     */
    private String content;

}
