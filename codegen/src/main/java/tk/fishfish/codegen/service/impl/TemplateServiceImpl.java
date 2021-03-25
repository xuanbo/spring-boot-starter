package tk.fishfish.codegen.service.impl;

import jetbrick.bean.BeanMap;
import jetbrick.template.JetEngine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.zeroturnaround.zip.ZipUtil;
import tk.fishfish.codegen.condition.DatabaseCondition;
import tk.fishfish.codegen.condition.TemplateCondition;
import tk.fishfish.codegen.config.CodegenProperties;
import tk.fishfish.codegen.dto.Table;
import tk.fishfish.codegen.service.DatabaseService;
import tk.fishfish.codegen.service.TemplateService;
import tk.fishfish.codegen.util.StringUtils;
import tk.fishfish.rest.execption.BizException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;

/**
 * 模板
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    private final DatabaseService databaseService;

    private final JetEngine engine;

    private final CodegenProperties properties;

    @Override
    public void gen(TemplateCondition condition, OutputStream out) throws IOException {
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        log.debug("代码生成: {}", id);
        Path srcPath = Paths.get("tmp", id);
        Path zipPath = Paths.get("tmp", id + ".zip");
        try {
            List<Table> tables = condition.getTables();
            for (Table table : tables) {
                log.debug("渲染表: {}", table.getName());
                DatabaseCondition.TableQuery query = createTableQuery(condition, table);
                // 字段
                List<Table.Column> columns = databaseService.table(query).getColumns();
                table.setColumns(columns);
                setTable(condition, table);
                resolveVariables(table);
                // 渲染
                render(id, table);
            }
            ZipUtil.pack(srcPath.toFile(), zipPath.toFile());
            Files.copy(zipPath, out);
        } finally {
            try {
                FileUtils.deleteDirectory(srcPath.toFile());
            } catch (IOException e) {
                // ignore
            }
            try {
                Files.deleteIfExists(zipPath);
            } catch (IOException e) {
                // ignore
            }
        }
    }

    private DatabaseCondition.TableQuery createTableQuery(TemplateCondition condition, Table table) {
        DatabaseCondition.TableQuery query = new DatabaseCondition.TableQuery();
        query.setId(condition.getId());
        query.setCatalog(table.getCatalog());
        query.setSchema(table.getSchema());
        query.setName(table.getName());
        return query;
    }

    private void setTable(TemplateCondition condition, Table table) {
        table.setPkg(condition.getPkg());
        table.setPrefix(condition.getPrefix());
        table.setAuthor(condition.getAuthor());
    }

    private void resolveVariables(Table table) {
        // 自己配置了实体名称，直接使用
        String entity = table.getEntity();
        if (jetbrick.util.StringUtils.isBlank(entity)) {
            // 否则去除表前缀，下划线转驼峰
            String name = table.getName();
            String prefix = table.getPrefix();
            entity = Optional.ofNullable(jetbrick.util.StringUtils.removeStart(name, prefix))
                    .map(StringUtils::toCamelCase)
                    .map(e -> e.substring(0, 1).toUpperCase() + e.substring(1))
                    .orElseThrow(() -> BizException.of(400, "表[" + name + "]移除前缀[" + prefix + "]后，字段将会为空"));
            table.setEntity(entity);
        }
        // 作者名称
        String author = table.getAuthor();
        if (jetbrick.util.StringUtils.isBlank(author)) {
            table.setAuthor(properties.getAuthor());
        }
    }

    private void render(String id, Table table) throws IOException {
        String parent = Paths.get("tmp", id, "src", "main", "java").toString();
        BeanMap variable = new BeanMap(table);
        Map<String, Object> templatePathVariable = createTemplatePathVariable(table);
        Map<String, String> templates = properties.getTemplates();
        for (Map.Entry<String, String> entry : templates.entrySet()) {
            String templateName = "templates/" + entry.getKey() + ".jetx";
            String templatePath = renderToString(entry.getValue(), templatePathVariable);
            log.debug("{} -> {}", templateName, templatePath);
            Path path = Paths.get(parent, templatePath);
            // 创建本地模板
            FileUtils.forceMkdirParent(path.toFile());
            // 渲染模板内容
            engine.getTemplate(templateName).render(variable, Files.newOutputStream(path, StandardOpenOption.CREATE));
        }
    }

    private Map<String, Object> createTemplatePathVariable(Table table) {
        // 模板路径变量
        Map<String, Object> templatePathVariable = new HashMap<>(4);
        templatePathVariable.put("pkg", table.getPkg().replaceAll("\\.", Matcher.quoteReplacement(File.separator)));
        templatePathVariable.put("separator", File.separator);
        templatePathVariable.put("entity", table.getEntity());
        return templatePathVariable;
    }

    private String renderToString(String source, Map<String, Object> variable) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            engine.createTemplate(source).render(variable, out);
            return out.toString();
        } catch (IOException e) {
            throw BizException.of(500, "渲染模板失败: " + e.getMessage(), e);
        }
    }

}
