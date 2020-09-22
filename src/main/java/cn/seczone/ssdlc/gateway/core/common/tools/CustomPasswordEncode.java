package cn.seczone.ssdlc.gateway.core.common.tools;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by xiongshuaishuai@seczone.cn on 2019/4/12.
 * 提供BCryptPasswordEncoder类的密码加密及比较算法方便调用。
 */
public class CustomPasswordEncode extends BCryptPasswordEncoder {
    private static BCryptPasswordEncoder bcryptEncode = new BCryptPasswordEncoder();

    /**
     * 密码加密算法，接收明文密码返回加密后的密码
     * @param plaintextPassword 明文密码
     * @return
     */
    public static String encryptPassword(String plaintextPassword) {
        return bcryptEncode.encode(plaintextPassword);
    }

    /**
     * 密码验证
     *
     * @param inputPwd 明文
     * @param encodedPwd 加密后的密码
     */
    public static boolean checkInputPwd(String inputPwd, String encodedPwd) {
        return bcryptEncode.matches(inputPwd, encodedPwd);
    }
}
