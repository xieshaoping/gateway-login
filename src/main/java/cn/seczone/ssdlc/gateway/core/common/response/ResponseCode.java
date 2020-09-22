package cn.seczone.ssdlc.gateway.core.common.response;

/**
 * Create by wudi@seczone.cn on 2017/8/9 设置http响应状态码常量
 * @author XieShaoping
 */
public enum ResponseCode {
	// 返回状态正常的状态码
	ERROR(0, "ERROR"),                       //错误
	SUCCESS(1, "SUCCESS"),                   //成功
	VALID_TIP(2, "MESSAGE TIP"),             //有效提示
	NEED_LOGIN(10, "NEED_LOGIN"),            //需要登录
	ILLEGAL_ARGUMENT(11, "ILLEGAL_ARGUMENT"),//授权问题
	LICESE_ERROR(30, "LICENSE_ERROR"),       //许可错误
	AGENT_ERROR(40, "AGENT_DETECT_DISABLE"), //代理程序错误
	AGENT_GLOBAL_ERROR(50, "AGENT_GLOBAL_DISABLE");//代理全局错误

	private final int code;
	private final String description;

	ResponseCode(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

}
