package tk.fishfish.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import tk.fishfish.excel.convert.BooleanConverter;
import tk.fishfish.excel.convert.EnumTypeConverter;

import java.util.Date;

/**
 * 用户
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Data
public class User {

    @ExcelProperty("用户名")
    private String name;

    @ExcelProperty("账号")
    private String username;

    @ExcelProperty("密码")
    private String password;

    @ExcelProperty(value = "性别", converter = EnumTypeConverter.class)
    private Sex sex;

    @ExcelProperty("录入时间")
    private Date createTime;

    @ExcelProperty(value = "是否启用", converter = BooleanConverter.class)
    private Boolean enable;

}
