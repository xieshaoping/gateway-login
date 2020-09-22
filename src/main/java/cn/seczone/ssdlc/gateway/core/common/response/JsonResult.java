package cn.seczone.ssdlc.gateway.core.common.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;


/**
 * 用于封装AJAX调用以后的JSON返回值
 * 正确返回值: {state:1, data:返回数据, message:正确消息}
 * 错误返回值: {state:0或者其他定义好的数字, data:null, message:错误消息}
 */


/**
 * Created by wudi@seczone.cn on 2017/8/8. 统一的Response Json格式
 */
// 保证序列化json的时候,如果是null的对象,key也会消失
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonResult<T> implements Serializable {

    private int status;

    private T data;

    private String msg;

    private JsonResult(int status) {
        this.status = status;
    }

    private JsonResult(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private JsonResult(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private JsonResult(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    @JsonIgnore
    // 使之不在json序列化结果当中
    public boolean isSuccess() {
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    // 成功响应静态方法组
    public static <T> JsonResult<T> createBySuccess() {
        return new JsonResult<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> JsonResult<T> createByTokenSuccess() {
        return new JsonResult<T>(2);
    }

    public static <T> JsonResult<T> createBySuccessMessage(String msg) {
        return new JsonResult<T>(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> JsonResult<T> createBySuccess(T data) {
        return new JsonResult<T>(ResponseCode.SUCCESS.getCode(), data);
    }

    public static <T> JsonResult<T> createBySuccess(String msg, T data) {
        return new JsonResult<T>(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> JsonResult<T> createByBooleanSuccess(Boolean data) {
        if (data == true) {
            return createBySuccess();
        } else {
            return createByError();
        }
    }

    // 信息提示响应结果方法组
    public static <T> JsonResult<T> createByFormValid(String msg) {
        return new JsonResult<T>(ResponseCode.VALID_TIP.getCode(), msg);
    }

    public static <T> JsonResult<T> createByFormValid(String msg, T data) {
        return new JsonResult<T>(ResponseCode.VALID_TIP.getCode(), msg, data);
    }


    // 失败响应结果方法组
    public static <T> JsonResult<T> createByError() {
        return new JsonResult<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDescription());
    }

    public static <T> JsonResult<T> createByError(T data) {
        return new JsonResult<T>(ResponseCode.ERROR.getCode(), data);
    }

    public static <T> JsonResult<T> createByError(String msg, T data) {
        return new JsonResult<T>(ResponseCode.ERROR.getCode(), msg, data);
    }

    public static <T> JsonResult<T> createByErrorMessage(String errorMessage) {
        return new JsonResult<T>(ResponseCode.ERROR.getCode(), errorMessage);
    }

    public static <T> JsonResult<T> createByNeedLogin(String errorMessage) {
        return new JsonResult<T>(ResponseCode.NEED_LOGIN.getCode(), errorMessage);
    }

    public static <T> JsonResult<T> createByNeedLogin() {
        return new JsonResult<T>(ResponseCode.NEED_LOGIN.getCode(), ResponseMsg.LOGIN_AGAIN);
    }

    public static <T> JsonResult<T> createByAgentGlobalDisable(String errorMessage) {
        return new JsonResult<T>(ResponseCode.AGENT_GLOBAL_ERROR.getCode(), errorMessage);
    }

    public static <T> JsonResult<T> createByIllegalMessage(String errorMessage) {
        return new JsonResult<T>(ResponseCode.ILLEGAL_ARGUMENT.getCode(), errorMessage);
    }

    public static <T> JsonResult<T> createByLicenseErrorMessage(String errorMessage) {
        return new JsonResult<T>(ResponseCode.LICESE_ERROR.getCode(), errorMessage);
    }

    //email重复
    public static <T> JsonResult<T> createByCheckError(String errorMessage) {
        return new JsonResult<T>(3, errorMessage);
    }

}
