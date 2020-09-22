package cn.seczone.ssdlc.gateway.core.service;

import cn.seczone.ssdlc.gateway.core.common.Exception.BadCredentialsException;
import cn.seczone.ssdlc.gateway.core.common.Exception.VerifyException;
import cn.seczone.ssdlc.gateway.core.common.constant.GlobalTipMsg;
import cn.seczone.ssdlc.gateway.core.common.tools.CustomPasswordEncode;
import cn.seczone.ssdlc.gateway.core.common.tools.Utils;
import cn.seczone.ssdlc.gateway.core.constant.AuthConsts;
import cn.seczone.ssdlc.gateway.core.dao.UserRepository;
import cn.seczone.ssdlc.gateway.core.entity.AuthorizationStrategyBuilder;
import cn.seczone.ssdlc.gateway.core.entity.Group;
import cn.seczone.ssdlc.gateway.core.entity.LoginUser;
import cn.seczone.ssdlc.gateway.core.entity.UserAuthMsg;
import cn.seczone.ssdlc.gateway.core.entity.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.WebSession;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author XieShaoping
 */
@Service
@Slf4j
public class SUserService {

    @Autowired
    UserRepository suserRepository;

    @Resource
    SUserService suserService;

    @Resource
    AuthorizationStrategyBuilder authorizationStrategyBuilder;

    @Resource
    UserService userService;

    public UserDetails findUserByEmail(UserVo userVo, WebSession session) throws VerifyException, BadCredentialsException {
        //password加密
        //String encryptPassword = CustomPasswordEncode.encryptPassword(userVo.getPassword());
        UserAuthMsg userAuthMsg = suserRepository.findUserByEmail(userVo);
        boolean result = CustomPasswordEncode.checkInputPwd(userVo.getPassword(), userAuthMsg.getPassword());
        if (!result) {
            throw new BadCredentialsException(GlobalTipMsg.BAD_CREDENTIALS);
        }
        Integer loginNum = userAuthMsg.getLoginCount();
        if (loginNum != null && loginNum > 1) {
            throw new VerifyException(GlobalTipMsg.LOGIN_EXCESSIVE);
        }
        List<GrantedAuthority> authorities = buildUserAuthority(userAuthMsg);
        UserDetails userDetails = buildUserForAuthentication(userAuthMsg, authorities);
        session.getAttributes().put(AuthConsts.SPRING_SECURITY_CONTEXT, userDetails);

        LoginUser loginUser = (LoginUser) userDetails;
        if (AuthConsts.ADMIN_ROLE_CODE.equals(loginUser.getRoleCode()) ||
                AuthConsts.USER_ROLE_CODE.equals(loginUser.getRoleCode())) {
            loginUser.setRoleCode(loginUser.getRoleCode());
        }
        //错误登录计数清零
        if (loginUser.getLoginCount() > 0) {
            userService.clearLoginCountByUserName(loginUser.getUsername());
        }
        return userDetails;
    }

    public List<Group> getGroupByOrgId(long orgId) {
        return suserRepository.getGroupByOrgId( orgId);
    }

    /**
     * 准备验证的数据，同时把用户相关数据放到UserDetails中
     *
     * @param userAuthMsg
     * @param authorities
     * @return
     */
    private UserDetails buildUserForAuthentication(UserAuthMsg userAuthMsg, List<GrantedAuthority> authorities) {
        //set 用户名密码
        LoginUser loginUser = new LoginUser(
                userAuthMsg.getEmail(),
                "",
                true,
                true,
                true,
                true,
                authorities);
        //set用户相关信息，如：登陆成功后要返回给前端的一些用户信息
        loginUser.setId(userAuthMsg.getId());
        loginUser.setUserUuid(userAuthMsg.getUserUuid());
        loginUser.setFirstName(userAuthMsg.getFirstName());
        loginUser.setLastName(userAuthMsg.getLastName());
        loginUser.setFullName(userAuthMsg.getFirstName() + userAuthMsg.getLastName());
        loginUser.setStatus(userAuthMsg.getStatus());
        loginUser.setAvatarPath(userAuthMsg.getAvatarPath());
        loginUser.setRoleCode(userAuthMsg.getRoleCode());
        loginUser.setGroupUuid(userAuthMsg.getGroupUuid());
        loginUser.setGroupName(userAuthMsg.getGroupName());
        loginUser.setOrgUuid(userAuthMsg.getOrgUuid());
        loginUser.setOrgId(userAuthMsg.getOrgId());
        loginUser.setGroupId(userAuthMsg.getGroupId());
        loginUser.setRoleId(userAuthMsg.getRoleId());
        loginUser.setLoginCount(userAuthMsg.getLoginCount());
        //存放用户的groupid信息
        List<Long> list2 = new ArrayList<Long>();
        if (userAuthMsg.getGroupId() != 0) {
            //groupadmin
            list2.add(userAuthMsg.getGroupId());
        } else {
            //orgadmin 没有groupid
            if (userAuthMsg.getOrgId() != 0) {
                //List<Group> groupList = groupDao.getGroupListByOrgId(userAuthMsg.getOrgId());
                List<Group> groupList = suserService.getGroupByOrgId(userAuthMsg.getOrgId());
                for (Group group : groupList) {
                    list2.add(group.getId());
                }
            }
        }
        loginUser.setGroupIdList(list2);
        List<String> list = new ArrayList<>();
        if (Utils.isEmpty(userAuthMsg.getGroupUuid())) {
            if (userAuthMsg.getOrgId() != 0) {
                List<Group> groupList = suserService.getGroupByOrgId(userAuthMsg.getOrgId());
                for (Group group : groupList) {
                    list.add(group.getGroupUuid());
                }
            }
        } else {
            list.add(userAuthMsg.getGroupUuid());
        }
        loginUser.setGroupUuidList(list);
        return loginUser;
    }

    /**
     * 构建用户权限信息
     * @param userAuthMsg
     * @return
     */
    private List<GrantedAuthority> buildUserAuthority(UserAuthMsg userAuthMsg) {
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        try {
            String roleCode = userAuthMsg.getRoleCode() ;


            List<String> list2 = new ArrayList<String>();
            if(Utils.isEmpty(userAuthMsg.getGroupUuid())){
                //orgadmin或者superadmin
                if(userAuthMsg.getOrgId()!=0){
                    List<Group> groupList  = suserService.getGroupByOrgId(userAuthMsg.getOrgId()) ;
                    for (Group group:groupList){
                        //orgadmin有多个group信息
                        list2.add(group.getGroupUuid()) ;
                    }
                }else{
                    //注释superadmin权限
					/*List<Group> groupList  =  groupDao.getAllGroupList() ;
					for (Group group:groupList){
						//superadmin有多个group信息
						list2.add(group.getGroupUuid()) ;
					}*/
                }
            }else{
                list2.add(userAuthMsg.getGroupUuid()) ;
            }
            authorizationStrategyBuilder.getBuildAuthorizationStrategy(userAuthMsg.getOrgId())
                    .buildAuthorization(authList,list2,roleCode);
        }catch (Exception e){
            log.error("user login buildAuthority execption :" + e.getStackTrace());
        }
        return authList;
    }

}
