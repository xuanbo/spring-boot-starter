package tk.fishfish.admin.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tk.fishfish.admin.entity.Audit;
import tk.fishfish.admin.entity.enums.AuditStatus;
import tk.fishfish.admin.entity.enums.AuditType;
import tk.fishfish.admin.service.AuditService;
import tk.fishfish.admin.util.http.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * TokenEndpoint切面，记录登录审计日志
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class TokenEndpointAspect {

    private final AuditService auditService;

    @Around("execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.postAccessToken(..))")
    public Object aroundPostAccessToken(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String username = null;
        String message = null;
        try {
            @SuppressWarnings("unchecked")
            Map<String, String> parameters = (Map<String, String>) args[1];
            username = parameters.get("username");
            return joinPoint.proceed(args);
        } catch (Throwable t) {
            message = t.getMessage();
            throw t;
        } finally {
            doLoginAudit(username, message);
        }
    }

    private void doLoginAudit(String username, String message) {
        if (username == null) {
            return;
        }
        Audit audit = new Audit();
        audit.setUsername(username);
        audit.setType(AuditType.LOGIN);
        audit.setStatus(message == null ? AuditStatus.SUCCESS : AuditStatus.FAILURE);
        audit.setMessage(message);
        audit.setCreatedAt(new Date());
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                    .getRequest();
            audit.setIp(RequestUtils.getIpAddress(request));
            audit.setUserAgent(request.getHeader("user-agent"));
            auditService.insertAsync(audit);
        } catch (Exception e) {
            log.warn("记录登录审计日志错误", e);
        }
    }

}
