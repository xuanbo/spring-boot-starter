package tk.fishfish.excel.convert;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * 布尔转换
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@SuppressWarnings("rawtypes")
public class BooleanConverter implements Converter<Boolean> {

    @Override
    public Class supportJavaTypeKey() {
        return Boolean.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Boolean convertToJavaData(CellData cellData, ExcelContentProperty property, GlobalConfiguration globalConfiguration) throws Exception {
        String value = cellData.getStringValue();
        if ("是".equals(value)) {
            return true;
        }
        if ("Y".equalsIgnoreCase(value)) {
            return true;
        }
        return "YES".equalsIgnoreCase(value);
    }

    @Override
    public CellData convertToExcelData(Boolean value, ExcelContentProperty property, GlobalConfiguration globalConfiguration) throws Exception {
        if (value == null) {
            return new CellData("否");
        }
        return value ? new CellData("是") : new CellData("否");
    }

}
