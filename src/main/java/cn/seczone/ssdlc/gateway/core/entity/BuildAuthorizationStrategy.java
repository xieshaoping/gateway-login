package cn.seczone.ssdlc.gateway.core.entity;

import cn.seczone.ssdlc.gateway.core.constant.PermissionMask;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * @author XieShaoping
 */
public interface BuildAuthorizationStrategy extends PermissionMask {

    void buildAuthorization(List<GrantedAuthority> authList, List<String> groupUuidList, String roleCode) ;

}
