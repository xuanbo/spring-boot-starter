package tk.fishfish.excel.convert;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import tk.fishfish.enums.EnumType;

/**
 * 枚举值转换
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@SuppressWarnings("rawtypes")
public class EnumTypeConverter implements Converter<EnumType> {

    @Override
    public Class supportJavaTypeKey() {
        return EnumType.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public EnumType convertToJavaData(CellData cellData, ExcelContentProperty property, GlobalConfiguration globalConfiguration) throws Exception {
        String value = cellData.getStringValue();
        Class<?> clazz = property.getField().getType();
        if (!clazz.isEnum()) {
            throw throwExcelDataConvertException(cellData, property, EnumTypeConverter.class.getName() + "必须使用在枚举上");
        }
        Object[] constants = clazz.getEnumConstants();
        // 枚举名称 > name > value
        for (Object obj : constants) {
            if (obj.toString().equalsIgnoreCase(value)) {
                return (EnumType) obj;
            }
            EnumType enumType = (EnumType) obj;
            if (enumType.getName().equalsIgnoreCase(value)) {
                return enumType;
            }
            if (enumType.getValue().equalsIgnoreCase(value)) {
                return enumType;
            }
        }
        throw throwExcelDataConvertException(cellData, property, "输入值[" + value + "]不合法，必须匹配枚举值");
    }

    private ExcelDataConvertException throwExcelDataConvertException(CellData cellData, ExcelContentProperty property, String message) {
        return new ExcelDataConvertException(cellData.getRowIndex(), cellData.getColumnIndex(), cellData, property, EnumTypeConverter.class.getName() + "必须使用在枚举上");
    }

    @Override
    public CellData convertToExcelData(EnumType enumType, ExcelContentProperty property, GlobalConfiguration globalConfiguration) throws Exception {
        return new CellData(enumType.getName());
    }

}
