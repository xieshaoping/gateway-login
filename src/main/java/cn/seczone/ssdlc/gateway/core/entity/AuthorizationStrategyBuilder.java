package cn.seczone.ssdlc.gateway.core.entity;

import org.springframework.stereotype.Service;

/**
 * @author XieShaoping
 */
@Service("returnAuthorizationStrategy")
public class AuthorizationStrategyBuilder {

    public BuildAuthorizationStrategy getBuildAuthorizationStrategy(long orgid) {
        //这里以后拓展的时候根据orgid的不同，确定不同的org,选择不同的‘build权限’策略
        return this.buildBuilding(orgid);
    }

    public BuildAuthorizationStrategy buildBuilding(long orgid) {
        return new DefaultBuldAuthorization();
    }

}
