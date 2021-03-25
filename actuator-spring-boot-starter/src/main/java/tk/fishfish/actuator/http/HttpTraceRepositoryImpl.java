package tk.fishfish.actuator.http;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;

import java.util.Collections;
import java.util.List;

/**
 * 记录超过时间的接口
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Slf4j
@RequiredArgsConstructor
public class HttpTraceRepositoryImpl implements HttpTraceRepository {

    private final long timeout;

    @Override
    public List<HttpTrace> findAll() {
        return Collections.emptyList();
    }

    @Override
    public void add(HttpTrace trace) {
        Long timeTaken = trace.getTimeTaken();
        if (timeTaken >= timeout) {
            String path = trace.getRequest().getUri().getPath();
            String query = trace.getRequest().getUri().getQuery();
            String method = trace.getRequest().getMethod();
            log.warn("监测到接口耗时[{}]ms, method: {}, path: {}, query: {}", timeTaken, method, path, query);
        }
    }

}
