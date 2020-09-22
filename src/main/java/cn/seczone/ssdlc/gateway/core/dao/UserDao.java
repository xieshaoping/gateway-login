package cn.seczone.ssdlc.gateway.core.dao;

import cn.seczone.ssdlc.gateway.core.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author XieShaoping
 */
@Mapper
public interface UserDao {
	/**修改用户*/
	boolean updateUser(User user);

	List<User> checkEmailExist(String email);

	boolean updateUserByEmail(User user);
}
