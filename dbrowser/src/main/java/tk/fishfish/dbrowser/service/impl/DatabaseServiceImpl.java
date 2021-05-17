package tk.fishfish.dbrowser.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.fishfish.dbrowser.condition.DatabaseCondition;
import tk.fishfish.dbrowser.database.Props;
import tk.fishfish.dbrowser.database.Session;
import tk.fishfish.dbrowser.database.SessionFactory;
import tk.fishfish.dbrowser.database.SessionHub;
import tk.fishfish.dbrowser.database.SqlResult;
import tk.fishfish.dbrowser.database.Table;
import tk.fishfish.dbrowser.entity.Database;
import tk.fishfish.dbrowser.service.DatabaseService;
import tk.fishfish.mybatis.service.impl.BaseServiceImpl;
import tk.fishfish.rest.model.ApiResult;

import java.util.List;

/**
 * 数据库
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Service
@RequiredArgsConstructor
public class DatabaseServiceImpl extends BaseServiceImpl<Database> implements DatabaseService {

    private final SessionHub sessionHub;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        super.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(List<String> ids) {
        super.deleteByIds(ids);
    }

    @Override
    public ApiResult<Void> ping(Database database) {
        String error;
        try {
            SessionFactory factory = SessionFactory.Companion.load(database.getUrl());
            Props props = createProps(database);
            error = factory.ping(props);
        } catch (Exception e) {
            error = e.getMessage();
        }
        if (error == null) {
            return ApiResult.ok(null);
        }
        return ApiResult.fail(500, error);
    }

    @Override
    public List<String> schemas(String id) {
        return sessionHub.get(id).schemas();
    }

    @Override
    public List<String> tables(String id, DatabaseCondition.Meta condition) {
        return sessionHub.get(id).tables(condition.getSchema());
    }

    @Override
    public Table table(String id, DatabaseCondition.Meta condition) {
        return sessionHub.get(id).table(condition.getSchema(), condition.getTable());
    }

    @Override
    public SqlResult execute(String id, DatabaseCondition.Execute condition) {
        return sessionHub.get(id).execute(condition.getSchema(), condition.getSql());
    }

    @Override
    public void load() {
        query(null).parallelStream().forEach(database -> {
            Session session = createSession(database);
            sessionHub.put(database.getId(), session);
        });
    }

    @Override
    public void afterInsert(Database database) {
        Session session = createSession(database);
        sessionHub.put(database.getId(), session);
    }

    @Override
    public void afterUpdate(Database database) {
        Session session = createSession(database);
        sessionHub.put(database.getId(), session);
    }

    @Override
    public void afterDelete(String id) {
        sessionHub.remove(id);
    }

    @Override
    public void afterDelete(List<String> ids) {
        ids.forEach(sessionHub::remove);
    }

    private Session createSession(Database database) {
        SessionFactory factory = SessionFactory.Companion.load(database.getUrl());
        Props props = createProps(database);
        return factory.create(props);
    }

    private Props createProps(Database database) {
        return new Props()
                .option("jdbcUrl", database.getUrl())
                .option("username", database.getUsername())
                .option("password", database.getPassword());
    }

}
