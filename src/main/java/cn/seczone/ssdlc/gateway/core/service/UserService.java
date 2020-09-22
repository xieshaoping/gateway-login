package cn.seczone.ssdlc.gateway.core.service;

/**
 * @author XieShaoping
 */
public interface UserService {

	void clearLoginCount(String userUuid);

	boolean updateLoginCount(String toString);

	int getUserLoginCount(String userName);

	void clearLoginCountByUserName(String userName);
}
