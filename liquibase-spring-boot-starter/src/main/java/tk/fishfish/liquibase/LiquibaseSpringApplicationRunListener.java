package tk.fishfish.liquibase;

import liquibase.database.DatabaseFactory;
import liquibase.datatype.DataTypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import tk.fishfish.liquibase.dm.DmBigIntType;
import tk.fishfish.liquibase.dm.DmBlobType;
import tk.fishfish.liquibase.dm.DmBooleanType;
import tk.fishfish.liquibase.dm.DmClobType;
import tk.fishfish.liquibase.dm.DmDatabase;
import tk.fishfish.liquibase.dm.DmDateTimeType;
import tk.fishfish.liquibase.dm.DmDecimalType;
import tk.fishfish.liquibase.dm.DmDoubleType;
import tk.fishfish.liquibase.dm.DmFloatType;
import tk.fishfish.liquibase.dm.DmIntType;
import tk.fishfish.liquibase.dm.DmNumberType;
import tk.fishfish.liquibase.dm.DmTimestampType;
import tk.fishfish.liquibase.dm.DmTinyIntType;
import tk.fishfish.liquibase.dm.DmVarcharType;

/**
 * 监听器，用于注册数据库适配
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Slf4j
public class LiquibaseSpringApplicationRunListener implements SpringApplicationRunListener {

    public LiquibaseSpringApplicationRunListener(SpringApplication application, String[] args) {
        log.debug("注册达梦数据库适配");
        // database
        DatabaseFactory.getInstance().register(new DmDatabase());
        // dataType
        DataTypeFactory.getInstance().register(DmBigIntType.class);
        DataTypeFactory.getInstance().register(DmBlobType.class);
        DataTypeFactory.getInstance().register(DmBooleanType.class);
        DataTypeFactory.getInstance().register(DmClobType.class);
        DataTypeFactory.getInstance().register(DmDateTimeType.class);
        DataTypeFactory.getInstance().register(DmDecimalType.class);
        DataTypeFactory.getInstance().register(DmDoubleType.class);
        DataTypeFactory.getInstance().register(DmFloatType.class);
        DataTypeFactory.getInstance().register(DmIntType.class);
        DataTypeFactory.getInstance().register(DmNumberType.class);
        DataTypeFactory.getInstance().register(DmTimestampType.class);
        DataTypeFactory.getInstance().register(DmTinyIntType.class);
        DataTypeFactory.getInstance().register(DmVarcharType.class);
    }

}
