package cn.seczone.ssdlc.gateway.core.service.Impl;


import cn.seczone.ssdlc.gateway.core.User;
import cn.seczone.ssdlc.gateway.core.common.tools.Utils;
import cn.seczone.ssdlc.gateway.core.dao.UserDao;

import cn.seczone.ssdlc.gateway.core.service.UserService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author XieShaoping
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    UserDao userDao;

    @Override
    public void clearLoginCount(String userUuid) {
        User user = new User();
        user.setUserUuid(userUuid);
        user.setLoginCount(0);
        userDao.updateUser(user);
    }

    @Override
    public boolean updateLoginCount(String email) {
        List<User> users = userDao.checkEmailExist(email);
        if (Utils.isEmpty(users)) {
            return false;
        }
        User user = new User();
        user.setEmail(email);
        user.setLoginCount(users.get(0).getLoginCount() + 1);
        return userDao.updateUserByEmail(user);
    }

    @Override
    public int getUserLoginCount(String userName) {
        List<User> users = userDao.checkEmailExist(userName);
        if (Utils.isEmpty(users)) {
            return 0;
        }
        int loginNum = users.get(0).getLoginCount();
        if (loginNum > 1) {
            return 1;
        }
        return 0;
    }

    @Override
    public void clearLoginCountByUserName(String userName) {
        User user = new User();
        user.setEmail(userName);
        user.setLoginCount(0);
        userDao.updateUserByEmail(user);
    }
}
