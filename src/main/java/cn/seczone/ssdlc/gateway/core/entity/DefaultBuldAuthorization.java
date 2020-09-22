package cn.seczone.ssdlc.gateway.core.entity;

import cn.seczone.ssdlc.gateway.core.constant.AuthConsts;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;


/**
 * @author XieShaoping
 */
public class DefaultBuldAuthorization implements BuildAuthorizationStrategy {

    @Override
    public void buildAuthorization(List<GrantedAuthority> authList, List<String> groupUuidList, String roleCode) {
        authList.add(new SimpleGrantedAuthority(roleCode));
        for (String groupUuid : groupUuidList) {
            /**
             * groupAdmin
             */
            //下面build用户权限
            authList.add(new SimpleGrantedAuthority(groupUuid + roleCode + AuthConsts.USER_GROUP));

            //下面build资产权限
            authList.add(new SimpleGrantedAuthority(groupUuid + roleCode + AuthConsts.ASSET_PROJECT));
            authList.add(new SimpleGrantedAuthority(groupUuid + roleCode + AuthConsts.ASSET_APP));
            authList.add(new SimpleGrantedAuthority(groupUuid + roleCode + AuthConsts.ASSET_SERVER));
            authList.add(new SimpleGrantedAuthority(groupUuid + roleCode + AuthConsts.ASSET_CONTAINER));
            authList.add(new SimpleGrantedAuthority(roleCode));
        }
    }
}
