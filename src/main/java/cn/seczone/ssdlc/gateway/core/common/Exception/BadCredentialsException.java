package cn.seczone.ssdlc.gateway.core.common.Exception;

import lombok.Data;

/**
 * @author XieShaoping
 * @date 2020/8/28
 * @description
 */
@Data
public class BadCredentialsException extends Exception{
    public BadCredentialsException(String message) {
        super(message);
    }
}
