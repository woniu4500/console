package com.jiuyv.hhc.model.security ;

// Generated by AutoCode4J
/**
 * EntityBean: 控制台机构表 : TBL_SYS_ORG
 * This is a value object(VO). 
 * Description: 
 * 
 * @author AutoCode4J
 * @version 1.0
 */
public class SysOrgVo implements java.io.Serializable {

	/** default Serial Version UID*/
	private static final long serialVersionUID = 1L;
	// -- Fields --
	/** orgCode : 机构代码. ORG_CODE: varchar(30) */
	private String orgCode ;
	
	/** version : VERSION. VERSION: int */
	private Long version ;
	
	/** fatherOrgCode : 上级机构代码. FATHER_ORG_CODE: varchar(30) */
	private String fatherOrgCode ;
	
	/** orgName : 机构名称. ORG_NAME: varchar(60) */
	private String orgName ;
	
	/** orgType : 机构类型. ORG_TYPE: varchar(10) */
	private String orgType ;
	
	/** persName : 联系人. PERS_NAME: varchar(20) */
	private String persName ;
	
	/** persPhone : 联系人电话. PERS_PHONE: varchar(20) */
	private String persPhone ;
	
	/** email : EMAIL. EMAIL: varchar(50) */
	private String email ;
	
	/** orgAddr : 地址. ORG_ADDR: varchar(200) */
	private String orgAddr ;
	
	/** status : 状态. STATUS: char(1) */
	private String status ;
	
	/** lastUptTime : 最后更新时间. LAST_UPT_TIME: char(14) */
	private String lastUptTime ;
	
	/** lastUptAcc : 最后更新账户. LAST_UPT_ACC: varchar(10) */
	private String lastUptAcc ;
	
	/** lastUptOrg : 最后更新机构. LAST_UPT_ORG: varchar(30) */
	private String lastUptOrg ;
	// -- Extend --
	/** 上级机构 */
	private String fatherOrgName ; 
	
	/** 机构类型 */
	private String orgTypeDesc ;
	
	/** 状态 */
	private String statusDesc;
	// -- Constructor --
	/**
	 * Constructor
	 * 
	 */
	public SysOrgVo() {
		// Default Construtor
	}
	
	public SysOrgVo(String orgCode) {
		this.orgCode = orgCode;
	}
		
	// -- Setter And Getter
	/**
	 * Set orgCode : 机构代码. ORG_CODE: varchar(30) 
	 */
	public void setOrgCode(String orgCode){
		this.orgCode = orgCode;	
	}
	
	/**
	 * Get orgCode : 机构代码. ORG_CODE: varchar(30) 
	 */
	public String getOrgCode(){
		return this.orgCode;	
	}
	
	/**
	 * Set version : VERSION. VERSION: int 
	 */
	public void setVersion(Long version){
		this.version = version;	
	}
	
	/**
	 * Get version : VERSION. VERSION: int 
	 */
	public Long getVersion(){
		return this.version;	
	}
	
	/**
	 * Set fatherOrgCode : 上级机构代码. FATHER_ORG_CODE: varchar(30) 
	 */
	public void setFatherOrgCode(String fatherOrgCode){
		this.fatherOrgCode = fatherOrgCode;	
	}
	
	/**
	 * Get fatherOrgCode : 上级机构代码. FATHER_ORG_CODE: varchar(30) 
	 */
	public String getFatherOrgCode(){
		return this.fatherOrgCode;	
	}
	
	/**
	 * Set orgName : 机构名称. ORG_NAME: varchar(60) 
	 */
	public void setOrgName(String orgName){
		this.orgName = orgName;	
	}
	
	/**
	 * Get orgName : 机构名称. ORG_NAME: varchar(60) 
	 */
	public String getOrgName(){
		return this.orgName;	
	}
	
	/**
	 * Set orgType : 机构类型. ORG_TYPE: varchar(10) 
	 */
	public void setOrgType(String orgType){
		this.orgType = orgType;	
	}
	
	/**
	 * Get orgType : 机构类型. ORG_TYPE: varchar(10) 
	 */
	public String getOrgType(){
		return this.orgType;	
	}
	
	/**
	 * Set persName : 联系人. PERS_NAME: varchar(20) 
	 */
	public void setPersName(String persName){
		this.persName = persName;	
	}
	
	/**
	 * Get persName : 联系人. PERS_NAME: varchar(20) 
	 */
	public String getPersName(){
		return this.persName;	
	}
	
	/**
	 * Set persPhone : 联系人电话. PERS_PHONE: varchar(20) 
	 */
	public void setPersPhone(String persPhone){
		this.persPhone = persPhone;	
	}
	
	/**
	 * Get persPhone : 联系人电话. PERS_PHONE: varchar(20) 
	 */
	public String getPersPhone(){
		return this.persPhone;	
	}
	
	/**
	 * Set email : EMAIL. EMAIL: varchar(50) 
	 */
	public void setEmail(String email){
		this.email = email;	
	}
	
	/**
	 * Get email : EMAIL. EMAIL: varchar(50) 
	 */
	public String getEmail(){
		return this.email;	
	}
	
	/**
	 * Set orgAddr : 地址. ORG_ADDR: varchar(200) 
	 */
	public void setOrgAddr(String orgAddr){
		this.orgAddr = orgAddr;	
	}
	
	/**
	 * Get orgAddr : 地址. ORG_ADDR: varchar(200) 
	 */
	public String getOrgAddr(){
		return this.orgAddr;	
	}
	
	/**
	 * Set status : 状态. STATUS: char(1) 
	 */
	public void setStatus(String status){
		this.status = status;	
	}
	
	/**
	 * Get status : 状态. STATUS: char(1) 
	 */
	public String getStatus(){
		return this.status;	
	}
	
	/**
	 * Set lastUptTime : 最后更新时间. LAST_UPT_TIME: char(14) 
	 */
	public void setLastUptTime(String lastUptTime){
		this.lastUptTime = lastUptTime;	
	}
	
	/**
	 * Get lastUptTime : 最后更新时间. LAST_UPT_TIME: char(14) 
	 */
	public String getLastUptTime(){
		return this.lastUptTime;	
	}
	
	/**
	 * Set lastUptAcc : 最后更新账户. LAST_UPT_ACC: varchar(10) 
	 */
	public void setLastUptAcc(String lastUptAcc){
		this.lastUptAcc = lastUptAcc;	
	}
	
	/**
	 * Get lastUptAcc : 最后更新账户. LAST_UPT_ACC: varchar(10) 
	 */
	public String getLastUptAcc(){
		return this.lastUptAcc;	
	}
	
	/**
	 * Set lastUptOrg : 最后更新机构. LAST_UPT_ORG: varchar(30) 
	 */
	public void setLastUptOrg(String lastUptOrg){
		this.lastUptOrg = lastUptOrg;	
	}
	
	/**
	 * Get lastUptOrg : 最后更新机构. LAST_UPT_ORG: varchar(30) 
	 */
	public String getLastUptOrg(){
		return this.lastUptOrg;	
	}
	
	public String getFatherOrgName() {
		return fatherOrgName;
	}

	public void setFatherOrgName(String fatherOrgName) {
		this.fatherOrgName = fatherOrgName;
	}

	public String getOrgTypeDesc() {
		return orgTypeDesc;
	}

	public void setOrgTypeDesc(String orgTypeDesc) {
		this.orgTypeDesc = orgTypeDesc;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
}