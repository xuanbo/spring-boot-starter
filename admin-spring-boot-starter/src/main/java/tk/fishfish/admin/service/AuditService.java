package tk.fishfish.admin.service;

import org.springframework.scheduling.annotation.Async;
import tk.fishfish.admin.entity.Audit;

/**
 * 审计
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
public interface AuditService {

    @Async
    void insertAsync(Audit audit);

}
