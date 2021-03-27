package tk.fishfish.admin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.fishfish.admin.entity.Audit;
import tk.fishfish.admin.service.AuditService;
import tk.fishfish.mybatis.service.impl.BaseServiceImpl;

import javax.annotation.Priority;

/**
 * 审计
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Slf4j
@Service
@Priority(10)
@RequiredArgsConstructor
public class AuditServiceImpl extends BaseServiceImpl<Audit> implements AuditService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertAsync(Audit audit) {
        super.insert(audit);
    }

}
