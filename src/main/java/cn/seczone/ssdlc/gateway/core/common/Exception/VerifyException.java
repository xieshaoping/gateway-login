package cn.seczone.ssdlc.gateway.core.common.Exception;

import lombok.Data;

/**
 * @author XieShaoping
 * @date 2020/8/28
 * @description
 */
@Data
public class VerifyException extends Exception{
    public VerifyException(String message) {
        super(message);
    }
}
