package cn.seczone.ssdlc.gateway.core.constant;

/**
 * @author XieShaoping
 */
public final class AuthConsts {

    private AuthConsts() {
    }

    public static final String ADMIN_ROLE_CODE = "Admin";
    public static final String USER_ROLE_CODE = "User";

    public static final String SUPER_ADMIN = "superAdmin";
    public static final String ORG_ADMIN = "orgAdmin";

    public static final String GROUP_ADMIN = "groupAdmin";

    //这个user的权限空置，所有team都是groupuser
    public static final String GROUP_USER = "groupUser";

    public static final String SECURITY_ADMIN = "securityAdmin";

    public static final String SECURITY_USER = "securityUser";

    public static final String DEV_ADMIN = "devAdmin";

    public static final String DEV_USER = "devUser";

    public static final String OP_ADMIN = "opAdmin";

    public static final String OP_USER = "opUser";

    /**
     * 以下是SID的三段拼接字符串
     */
    public static final String ASSET_PROJECT = "Project";

    public static final String ASSET_APP = "App";

    public static final String ASSET_SERVER = "Server";

    public static final String ASSET_CONTAINER = "Container";

    public static final String USER_GROUP = "UserGroup";

    public static final String USER_TEAM = "UserTeam";

    public static final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";

}
