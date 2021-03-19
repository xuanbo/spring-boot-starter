package tk.fishfish.codegen.startup;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tk.fishfish.codegen.config.datasource.DataSourceHub;
import tk.fishfish.codegen.service.DatabaseService;

import javax.sql.DataSource;

/**
 * 启动加载数据源
 *
 * @author 奔波儿灞
 * @since 1.5.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataSourceStartup implements CommandLineRunner {

    private final DatabaseService databaseService;
    private final DataSourceHub dataSourceHub;

    @Override
    public void run(String... args) {
        log.info("加载数据源");
        databaseService.query(null).forEach(e -> {
            DataSource dataSource = dataSourceHub.createDataSource(e);
            dataSourceHub.put(e.getId(), dataSource);
        });
    }

}
