package cn.seczone.ssdlc.gateway.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

/**
 * @author XieShaoping
 * @date 2019/4/16
 */
@Data
public class LoginUser extends User {
    @JsonIgnore
    private Long id;
    private String userUuid;
    private String firstName;
    private String lastName;
    private String fullName ;
    private Integer status;
    private String avatarPath;
    @JsonIgnore
    private long roleId;
    private String roleCode;
    @JsonIgnore
    private long groupId;
    private String groupUuid;
    private String groupName;
    private List<Long> groupIdList;
    private List<String> groupUuidList;
    @JsonIgnore
    private long orgId;
    private String orgUuid;
    private int loginCount;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public String getGroupName() {
        return groupName;
    }


    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Long> getGroupIdList() {
        return groupIdList;
    }

    public void setGroupIdList(List<Long> groupIdList) {
        this.groupIdList = groupIdList;
    }

    public List<String> getGroupUuidList() {
        return groupUuidList;
    }

    public void setGroupUuidList(List<String> groupUuidList) {
        this.groupUuidList = groupUuidList;
    }


    public long getRoleId() {
        return roleId;
    }


    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }


    public long getGroupId() {
        return groupId;
    }


    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }


    public long getOrgId() {
        return orgId;
    }


    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public LoginUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }



    public LoginUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean
            credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }


    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getUserUuid() {
        return userUuid;
    }


    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }


    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public Integer getStatus() {
        return status;
    }


    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }


    public String getRoleCode() {
        return roleCode;
    }


    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }


    public String getGroupUuid() {
        return groupUuid;
    }


    public void setGroupUuid(String groupUuid) {
        this.groupUuid = groupUuid;
    }

    public String getOrgUuid() {
        return orgUuid;
    }

    public void setOrgUuid(String orgUuid) {
        this.orgUuid = orgUuid;
    }
}
