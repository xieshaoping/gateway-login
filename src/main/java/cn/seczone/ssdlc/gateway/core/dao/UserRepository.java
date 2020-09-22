package cn.seczone.ssdlc.gateway.core.dao;

import cn.seczone.ssdlc.gateway.core.entity.Group;
import cn.seczone.ssdlc.gateway.core.entity.SUser;
import cn.seczone.ssdlc.gateway.core.entity.UserAuthMsg;
import cn.seczone.ssdlc.gateway.core.entity.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/*import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;*/

/**
 * @author XieShaoping
 */
@Mapper
@Component
public interface UserRepository {

    SUser login(String email, String password);

    SUser findByEmailAndPassword(String email, String password);

    UserAuthMsg findUserByEmail(UserVo userVo);

    List<Group> getGroupByOrgId(long orgId);

}
