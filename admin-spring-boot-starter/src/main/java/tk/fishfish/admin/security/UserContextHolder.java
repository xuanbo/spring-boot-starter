package tk.fishfish.admin.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 用户上下文
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
public final class UserContextHolder {

    private UserContextHolder() {
        throw new IllegalStateException("Utils");
    }

    public static DefaultUserDetails current() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof DefaultUserDetails) {
            return (DefaultUserDetails) principal;
        }
        return null;
    }

    public static String username() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        return authentication.getName();
    }

}
