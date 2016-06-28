package com.jiuyv.hhc.model.customer;

/**
 * EntityBean: 会员基本信息表 : TBL_WEB_MEMBER_INFO This is a value object(VO).
 * Description:
 * 
 * @author AutoCode4J
 * @version 1.0
 */
public class WebMemberInfoVo implements java.io.Serializable {

	/** default Serial Version UID. */
	private static final long serialVersionUID = 1L;
	// -- Fields --
	/** customerCode : 会员代码. CUSTOMER_CODE: NUMBER(10) */
	private Long customerCode;

	/** version : VERSION. VERSION: NUMBER(10) */
	private Long version;

	/** customerType : 会员类型. CUSTOMER_TYPE: NVARCHAR2(1) */
	private String customerType;

	/** fatherCustomerCode : 代理会员代码. FATHER_CUSTOMER_CODE: NUMBER(10) */
	private Long fatherCustomerCode;

	/** rcmdCode : 推荐号. RCMD_CODE: NVARCHAR2(50) */
	private String rcmdCode;

	/** mobile : 手机号码. MOBILE: NVARCHAR2(50) */
	private String mobile;

	/** password : 密码. PASSWORD: NVARCHAR2(50) */
	private String password;

	/** realName : 姓名. REAL_NAME: NVARCHAR2(50) */
	private String realName;

	/** phone : 电话. PHONE: NVARCHAR2(50) */
	private String phone;

	/** idNumber : 证件号码. ID_NUMBER: NVARCHAR2(50) */
	private String idNumber;

	/** idType : 证件类型. ID_TYPE: NVARCHAR2(2) */
	private String idType;

	/** address : 地址. ADDRESS: NVARCHAR2(150) */
	private String address;

	/** zip : 邮编. ZIP: NVARCHAR2(50) */
	private String zip;

	/** face : 头像. FACE: NVARCHAR2(100) */
	private String face;

	/** email : 电子邮件. EMAIL: NVARCHAR2(150) */
	private String email;

	/** registerIp : 注册IP. REGISTER_IP: NVARCHAR2(50) */
	private String registerIp;

	/** linkCustomerSeq : 关联序号. LINK_CUSTOMER_SEQ: NUMBER(18) */
	private Long linkCustomerSeq;

	/** userState : 用户状态. USER_STATE: VARCHAR2(1) */
	private String userState;

	/** lastLoginTime : 最后登录时间. LAST_LOGIN_TIME: CHAR(14) */
	private String lastLoginTime;

	/** lastLoginIp : 最后登录IP. LAST_LOGIN_IP: NVARCHAR2(50) */
	private String lastLoginIp;

	/** lockTime : 上锁时间. LOCK_TIME: CHAR(14) */
	private String lockTime;

	/** passwdErr : 密码错次. PASSWD_ERR: NUMBER(10) */
	private Long passwdErr;

	/** recTime : 创建时间. REC_TIME: CHAR(14) */
	private String recTime;

	/** lastUptTime : 最后更新时间. LAST_UPT_TIME: CHAR(14) */
	private String lastUptTime;

	/** lastUptAcc : 最后更新账户. LAST_UPT_ACC: NVARCHAR2(30) */
	private String lastUptAcc;

	/** lastUptOrg : 最后更新机构. LAST_UPT_ORG: NVARCHAR2(30) */
	private String lastUptOrg;

	/** expireTime : 密码过期时间. EXPIRE_TIME: CHAR(14) */
	private String expireTime;

	// -- Constructor --
	/** userState : 用户状态. USER_STATE: VARCHAR2(1) */
	private String userStateDesc;

	/** idType : 证件类型. ID_TYPE: NVARCHAR2(2) */
	private String idTypeDesc;
	
	private String fatherCustomerCodeDesc;

	
	private String localState;
	
	/** localState : 是否审核通过 */
	private String auditState;
	
	private String auditStateDesc;
	
	/** localState : 审核不通过原因 */
	private String auditStateRemark;
	/** 运营证号*/
	private String lic;
	
	private String customerTypeDesc;
	
	// -- Constructor --
	/**
	 * Constructor.
	 */
	public WebMemberInfoVo() {
		// Default Construtor
	}

	/**
	 * Instantiates a new web member info vo.
	 * 
	 * @param customerCode
	 *            the customer code
	 */
	public WebMemberInfoVo(Long customerCode) {
		this.customerCode = customerCode;
	}

	// -- Setter And Getter
	/**
	 * Set customerCode : 会员代码. CUSTOMER_CODE: NUMBER(10)
	 * 
	 * @param customerCode
	 *            the new customerCode : 会员代码
	 */
	public void setCustomerCode(Long customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * Get customerCode : 会员代码. CUSTOMER_CODE: NUMBER(10)
	 * 
	 * @return the customerCode : 会员代码
	 */
	public Long getCustomerCode() {
		return this.customerCode;
	}

	/**
	 * Set version : VERSION. VERSION: NUMBER(10)
	 * 
	 * @param version
	 *            the new version : VERSION
	 */
	public void setVersion(Long version) {
		this.version = version;
	}

	/**
	 * Get version : VERSION. VERSION: NUMBER(10)
	 * 
	 * @return the version : VERSION
	 */
	public Long getVersion() {
		return this.version;
	}

	/**
	 * Set customerType : 会员类型. CUSTOMER_TYPE: NVARCHAR2(1)
	 * 
	 * @param customerType
	 *            the new customerType : 会员类型
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	/**
	 * Get customerType : 会员类型. CUSTOMER_TYPE: NVARCHAR2(1)
	 * 
	 * @return the customerType : 会员类型
	 */
	public String getCustomerType() {
		return this.customerType;
	}

	/**
	 * Set fatherCustomerCode : 代理会员代码. FATHER_CUSTOMER_CODE: NUMBER(10)
	 * 
	 * @param fatherCustomerCode
	 *            the new fatherCustomerCode : 代理会员代码
	 */
	public void setFatherCustomerCode(Long fatherCustomerCode) {
		this.fatherCustomerCode = fatherCustomerCode;
	}

	/**
	 * Get fatherCustomerCode : 代理会员代码. FATHER_CUSTOMER_CODE: NUMBER(10)
	 * 
	 * @return the fatherCustomerCode : 代理会员代码
	 */
	public Long getFatherCustomerCode() {
		return this.fatherCustomerCode;
	}

	/**
	 * Set rcmdCode : 推荐号. RCMD_CODE: NVARCHAR2(50)
	 * 
	 * @param rcmdCode
	 *            the new rcmdCode : 推荐号
	 */
	public void setRcmdCode(String rcmdCode) {
		this.rcmdCode = rcmdCode;
	}

	/**
	 * Get rcmdCode : 推荐号. RCMD_CODE: NVARCHAR2(50)
	 * 
	 * @return the rcmdCode : 推荐号
	 */
	public String getRcmdCode() {
		return this.rcmdCode;
	}

	/**
	 * Set mobile : 手机号码. MOBILE: NVARCHAR2(50)
	 * 
	 * @param mobile
	 *            the new mobile : 手机号码
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * Get mobile : 手机号码. MOBILE: NVARCHAR2(50)
	 * 
	 * @return the mobile : 手机号码
	 */
	public String getMobile() {
		return this.mobile;
	}

	/**
	 * Set password : 密码. PASSWORD: NVARCHAR2(50)
	 * 
	 * @param password
	 *            the new password : 密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Get password : 密码. PASSWORD: NVARCHAR2(50)
	 * 
	 * @return the password : 密码
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Set realName : 姓名. REAL_NAME: NVARCHAR2(50)
	 * 
	 * @param realName
	 *            the new realName : 姓名
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * Get realName : 姓名. REAL_NAME: NVARCHAR2(50)
	 * 
	 * @return the realName : 姓名
	 */
	public String getRealName() {
		return this.realName;
	}

	/**
	 * Set phone : 电话. PHONE: NVARCHAR2(50)
	 * 
	 * @param phone
	 *            the new phone : 电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Get phone : 电话. PHONE: NVARCHAR2(50)
	 * 
	 * @return the phone : 电话
	 */
	public String getPhone() {
		return this.phone;
	}

	/**
	 * Set idNumber : 证件号码. ID_NUMBER: NVARCHAR2(50)
	 * 
	 * @param idNumber
	 *            the new idNumber : 证件号码
	 */
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	/**
	 * Get idNumber : 证件号码. ID_NUMBER: NVARCHAR2(50)
	 * 
	 * @return the idNumber : 证件号码
	 */
	public String getIdNumber() {
		return this.idNumber;
	}

	/**
	 * Set idType : 证件类型. ID_TYPE: NVARCHAR2(2)
	 * 
	 * @param idType
	 *            the new idType : 证件类型
	 */
	public void setIdType(String idType) {
		this.idType = idType;
	}

	/**
	 * Get idType : 证件类型. ID_TYPE: NVARCHAR2(2)
	 * 
	 * @return the idType : 证件类型
	 */
	public String getIdType() {
		return this.idType;
	}

	/**
	 * Set address : 地址. ADDRESS: NVARCHAR2(150)
	 * 
	 * @param address
	 *            the new address : 地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Get address : 地址. ADDRESS: NVARCHAR2(150)
	 * 
	 * @return the address : 地址
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * Set zip : 邮编. ZIP: NVARCHAR2(50)
	 * 
	 * @param zip
	 *            the new zip : 邮编
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * Get zip : 邮编. ZIP: NVARCHAR2(50)
	 * 
	 * @return the zip : 邮编
	 */
	public String getZip() {
		return this.zip;
	}

	/**
	 * Set face : 头像. FACE: NVARCHAR2(100)
	 * 
	 * @param face
	 *            the new face : 头像
	 */
	public void setFace(String face) {
		this.face = face;
	}

	/**
	 * Get face : 头像. FACE: NVARCHAR2(100)
	 * 
	 * @return the face : 头像
	 */
	public String getFace() {
		return this.face;
	}

	/**
	 * Set email : 电子邮件. EMAIL: NVARCHAR2(150)
	 * 
	 * @param email
	 *            the new email : 电子邮件
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Get email : 电子邮件. EMAIL: NVARCHAR2(150)
	 * 
	 * @return the email : 电子邮件
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Set registerIp : 注册IP. REGISTER_IP: NVARCHAR2(50)
	 * 
	 * @param registerIp
	 *            the new registerIp : 注册IP
	 */
	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}

	/**
	 * Get registerIp : 注册IP. REGISTER_IP: NVARCHAR2(50)
	 * 
	 * @return the registerIp : 注册IP
	 */
	public String getRegisterIp() {
		return this.registerIp;
	}

	/**
	 * Set linkCustomerSeq : 关联序号. LINK_CUSTOMER_SEQ: NUMBER(18)
	 * 
	 * @param linkCustomerSeq
	 *            the new linkCustomerSeq : 关联序号
	 */
	public void setLinkCustomerSeq(Long linkCustomerSeq) {
		this.linkCustomerSeq = linkCustomerSeq;
	}

	/**
	 * Get linkCustomerSeq : 关联序号. LINK_CUSTOMER_SEQ: NUMBER(18)
	 * 
	 * @return the linkCustomerSeq : 关联序号
	 */
	public Long getLinkCustomerSeq() {
		return this.linkCustomerSeq;
	}

	/**
	 * Set userState : 用户状态. USER_STATE: VARCHAR2(1)
	 * 
	 * @param userState
	 *            the new userState : 用户状态
	 */
	public void setUserState(String userState) {
		this.userState = userState;
	}

	/**
	 * Get userState : 用户状态. USER_STATE: VARCHAR2(1)
	 * 
	 * @return the userState : 用户状态
	 */
	public String getUserState() {
		return this.userState;
	}

	/**
	 * Set lastLoginTime : 最后登录时间. LAST_LOGIN_TIME: CHAR(14)
	 * 
	 * @param lastLoginTime
	 *            the new lastLoginTime : 最后登录时间
	 */
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	/**
	 * Get lastLoginTime : 最后登录时间. LAST_LOGIN_TIME: CHAR(14)
	 * 
	 * @return the lastLoginTime : 最后登录时间
	 */
	public String getLastLoginTime() {
		return this.lastLoginTime;
	}

	/**
	 * Set lastLoginIp : 最后登录IP. LAST_LOGIN_IP: NVARCHAR2(50)
	 * 
	 * @param lastLoginIp
	 *            the new lastLoginIp : 最后登录IP
	 */
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	/**
	 * Get lastLoginIp : 最后登录IP. LAST_LOGIN_IP: NVARCHAR2(50)
	 * 
	 * @return the lastLoginIp : 最后登录IP
	 */
	public String getLastLoginIp() {
		return this.lastLoginIp;
	}

	/**
	 * Set lockTime : 上锁时间. LOCK_TIME: CHAR(14)
	 * 
	 * @param lockTime
	 *            the new lockTime : 上锁时间
	 */
	public void setLockTime(String lockTime) {
		this.lockTime = lockTime;
	}

	/**
	 * Get lockTime : 上锁时间. LOCK_TIME: CHAR(14)
	 * 
	 * @return the lockTime : 上锁时间
	 */
	public String getLockTime() {
		return this.lockTime;
	}

	/**
	 * Set passwdErr : 密码错次. PASSWD_ERR: NUMBER(10)
	 * 
	 * @param passwdErr
	 *            the new passwdErr : 密码错次
	 */
	public void setPasswdErr(Long passwdErr) {
		this.passwdErr = passwdErr;
	}

	/**
	 * Get passwdErr : 密码错次. PASSWD_ERR: NUMBER(10)
	 * 
	 * @return the passwdErr : 密码错次
	 */
	public Long getPasswdErr() {
		return this.passwdErr;
	}

	/**
	 * Set recTime : 创建时间. REC_TIME: CHAR(14)
	 * 
	 * @param recTime
	 *            the new recTime : 创建时间
	 */
	public void setRecTime(String recTime) {
		this.recTime = recTime;
	}

	/**
	 * Get recTime : 创建时间. REC_TIME: CHAR(14)
	 * 
	 * @return the recTime : 创建时间
	 */
	public String getRecTime() {
		return this.recTime;
	}

	/**
	 * Set lastUptTime : 最后更新时间. LAST_UPT_TIME: CHAR(14)
	 * 
	 * @param lastUptTime
	 *            the new lastUptTime : 最后更新时间
	 */
	public void setLastUptTime(String lastUptTime) {
		this.lastUptTime = lastUptTime;
	}

	/**
	 * Get lastUptTime : 最后更新时间. LAST_UPT_TIME: CHAR(14)
	 * 
	 * @return the lastUptTime : 最后更新时间
	 */
	public String getLastUptTime() {
		return this.lastUptTime;
	}

	/**
	 * Set lastUptAcc : 最后更新账户. LAST_UPT_ACC: NVARCHAR2(30)
	 * 
	 * @param lastUptAcc
	 *            the new lastUptAcc : 最后更新账户
	 */
	public void setLastUptAcc(String lastUptAcc) {
		this.lastUptAcc = lastUptAcc;
	}

	/**
	 * Get lastUptAcc : 最后更新账户. LAST_UPT_ACC: NVARCHAR2(30)
	 * 
	 * @return the lastUptAcc : 最后更新账户
	 */
	public String getLastUptAcc() {
		return this.lastUptAcc;
	}

	/**
	 * Set lastUptOrg : 最后更新机构. LAST_UPT_ORG: NVARCHAR2(30)
	 * 
	 * @param lastUptOrg
	 *            the new lastUptOrg : 最后更新机构
	 */
	public void setLastUptOrg(String lastUptOrg) {
		this.lastUptOrg = lastUptOrg;
	}

	/**
	 * Get lastUptOrg : 最后更新机构. LAST_UPT_ORG: NVARCHAR2(30)
	 * 
	 * @return the lastUptOrg : 最后更新机构
	 */
	public String getLastUptOrg() {
		return this.lastUptOrg;
	}

	/**
	 * Set expireTime : 密码过期时间. EXPIRE_TIME: CHAR(14)
	 * 
	 * @param expireTime
	 *            the new expireTime : 密码过期时间
	 */
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	/**
	 * Get expireTime : 密码过期时间. EXPIRE_TIME: CHAR(14)
	 * 
	 * @return the expireTime : 密码过期时间
	 */
	public String getExpireTime() {
		return this.expireTime;
	}

	/**
	 * 获取 userState : 用户状态.
	 * 
	 * @return the userStateDesc
	 */
	public String getUserStateDesc() {
		return userStateDesc;
	}

	/**
	 * 设置 userState : 用户状态.
	 * 
	 * @param userStateDesc
	 *            the userStateDesc to set
	 */
	public void setUserStateDesc(String userStateDesc) {
		this.userStateDesc = userStateDesc;
	}

	/**
	 * 获取 idType : 证件类型.
	 * 
	 * @return the idTypeDesc
	 */
	public String getIdTypeDesc() {
		return idTypeDesc;
	}

	/**
	 * 设置 idType : 证件类型.
	 * 
	 * @param idTypeDesc
	 *            the idTypeDesc to set
	 */
	public void setIdTypeDesc(String idTypeDesc) {
		this.idTypeDesc = idTypeDesc;
	}

	/**
	 * @return the fatherCustomerCodeDesc
	 */
	public String getFatherCustomerCodeDesc() {
		return fatherCustomerCodeDesc;
	}

	/**
	 * @param fatherCustomerCodeDesc the fatherCustomerCodeDesc to set
	 */
	public void setFatherCustomerCodeDesc(String fatherCustomerCodeDesc) {
		this.fatherCustomerCodeDesc = fatherCustomerCodeDesc;
	}

	public String getLocalState() {
		return localState;
	}

	public void setLocalState(String localState) {
		this.localState = localState;
	}

	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	public String getAuditStateRemark() {
		return auditStateRemark;
	}

	public void setAuditStateRemark(String auditStateRemark) {
		this.auditStateRemark = auditStateRemark;
	}

	public String getLic() {
		return lic;
	}

	public void setLic(String lic) {
		this.lic = lic;
	}

	public String getCustomerTypeDesc() {
		return customerTypeDesc;
	}

	public void setCustomerTypeDesc(String customerTypeDesc) {
		this.customerTypeDesc = customerTypeDesc;
	}

	public String getAuditStateDesc() {
		return auditStateDesc;
	}

	public void setAuditStateDesc(String auditStateDesc) {
		this.auditStateDesc = auditStateDesc;
	}

	
	
}