package cn.seczone.ssdlc.gateway.core.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class Group implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String groupUuid;
	private String groupName;
	private long orgId;
	private int status;
	private String timeZone;
	private int dateFormat;
	private int timeFormat;
	private Timestamp createTime;
	private Timestamp modifyTime;
	private String agentToken;
	private int defaultGroup;
	private boolean autoScaLicense ;
	private int isAutoScaLicense;
	private int autoScaLicenseNum;
	private String hashPrefix ;
	private int count ;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getGroupUuid() {
		return groupUuid;
	}

	public void setGroupUuid(String groupUuid) {
		this.groupUuid = groupUuid;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public int getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(int dateFormat) {
		this.dateFormat = dateFormat;
	}

	public int getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(int timeFormat) {
		this.timeFormat = timeFormat;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getAgentToken() {
		return agentToken;
	}

	public void setAgentToken(String agentToken) {
		this.agentToken = agentToken;
	}

	public int getDefaultGroup() {
		return defaultGroup;
	}

	public void setDefaultGroup(int defaultGroup) {
		this.defaultGroup = defaultGroup;
	}

	public boolean isAutoScaLicense() {
		return autoScaLicense;
	}

	public void setAutoScaLicense(boolean autoScaLicense) {
		this.autoScaLicense = autoScaLicense;
	}

	public String getHashPrefix() {
		return hashPrefix;
	}

	public void setHashPrefix(String hashPrefix) {
		this.hashPrefix = hashPrefix;
	}

	public int getIsAutoScaLicense() {
		return isAutoScaLicense;
	}

	public void setIsAutoScaLicense(int isAutoScaLicense) {
		this.isAutoScaLicense = isAutoScaLicense;
	}

	public int getAutoScaLicenseNum() {
		return autoScaLicenseNum;
	}

	public void setAutoScaLicenseNum(int autoScaLicenseNum) {
		this.autoScaLicenseNum = autoScaLicenseNum;
	}
}
