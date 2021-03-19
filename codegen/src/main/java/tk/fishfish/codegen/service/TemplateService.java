package tk.fishfish.codegen.service;

import tk.fishfish.codegen.condition.TemplateCondition;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 模板
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
public interface TemplateService {

    /**
     * 生成模板代码，并压缩为zip文件
     *
     * @param condition 条件
     * @param out       输出
     * @throws IOException IO异常
     */
    void gen(TemplateCondition condition, OutputStream out) throws IOException;

}
