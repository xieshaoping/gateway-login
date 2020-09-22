package cn.seczone.ssdlc.gateway.core.entity;


import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author XieShaoping
 */
@Data
public class LoginVo {

    @NotNull
    @Size(min = 6, max = 64, message = "email length error")

    private String username;

    @NotNull
    @Size(min = 5, max = 50, message = "password length error")
    private String password;

    private String captcha;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
