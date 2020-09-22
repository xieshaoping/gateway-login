package cn.seczone.ssdlc.gateway.core.entity;

import lombok.Data;

/**
 * @author XieShaoping
 */
@Data
public class SRole implements java.io.Serializable {
    private Integer id;
    private SUser SUser;
    private String name;

    public SRole() {
    }

    public SRole(SUser SUser) {
        this.SUser = SUser;
    }

    public SRole(SUser SUser, String name) {
        this.SUser = SUser;
        this.name = name;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SUser getSUser() {
        return this.SUser;
    }

    public void setSUser(SUser SUser) {
        this.SUser = SUser;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
