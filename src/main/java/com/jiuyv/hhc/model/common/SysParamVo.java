package com.jiuyv.hhc.model.common ;

// Generated by AutoCode4J
/**
 * EntityBean: 系统参数表 : TBL_SYS_PARAM
 * This is a value object(VO). 
 * Description: 
 * 
 * @author AutoCode4J
 * @version 1.0
 */
public class SysParamVo implements java.io.Serializable {

	/** default Serial Version UID*/
	private static final long serialVersionUID = 1L;
	// -- Fields --
	/** paramCode : 参数名称. PARAM_CODE: varchar(40) */
	private String paramCode ;
	
	/** version : VERSION. VERSION: int */
	private Long version ;
	
	/** paramValue : 参数值. PARAM_VALUE: varchar(100) */
	private String paramValue ;
	
	/** paramCheck : 检查方式. PARAM_CHECK: varchar(1) */
	private String paramCheck ;
	
	/** paramRule : 检查规则. PARAM_RULE: varchar(100) */
	private String paramRule ;
	
	/** paramModifyFlag : 可修改标志. PARAM_MODIFY_FLAG: varchar(1) */
	private String paramModifyFlag ;
	
	/** paramDesc : 描述. PARAM_DESC: varchar(255) */
	private String paramDesc ;
	
	/** paramRemark : 注释. PARAM_REMARK: varchar(255) */
	private String paramRemark ;
	
	/** lastUpdateOrg : 最后更新机构. LAST_UPDATE_ORG: varchar(30) */
	private String lastUpdateOrg ;
	
	/** lastUpdateAcc : 最后更新账户. LAST_UPDATE_ACC: varchar(10) */
	private String lastUpdateAcc ;
	
	/** lastUpdateTime : 最后更新时间. LAST_UPDATE_TIME: char(14) */
	private String lastUpdateTime ;
	
	/** reserved1 : 保留域1. RESERVED1: VARCHAR(100) */
	private String reserved1 ;
	
	/** reserved2 : 保留域2. RESERVED2: VARCHAR(100) */
	private String reserved2 ;
	
	// -- Constructor --
	/**
	 * Constructor
	 * 
	 */
	public SysParamVo() {
		// Default Construtor
	}
	
	public SysParamVo(String paramCode) {
		this.paramCode = paramCode;
	}
		
	// -- Setter And Getter
	/**
	 * Set paramCode : 参数名称. PARAM_CODE: varchar(40) 
	 */
	public void setParamCode(String paramCode){
		this.paramCode = paramCode;	
	}
	
	/**
	 * Get paramCode : 参数名称. PARAM_CODE: varchar(40) 
	 */
	public String getParamCode(){
		return this.paramCode;	
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
	 * Set paramValue : 参数值. PARAM_VALUE: varchar(100) 
	 */
	public void setParamValue(String paramValue){
		this.paramValue = paramValue;	
	}
	
	/**
	 * Get paramValue : 参数值. PARAM_VALUE: varchar(100) 
	 */
	public String getParamValue(){
		return this.paramValue;	
	}
	
	/**
	 * Set paramCheck : 检查方式. PARAM_CHECK: varchar(1) 
	 */
	public void setParamCheck(String paramCheck){
		this.paramCheck = paramCheck;	
	}
	
	/**
	 * Get paramCheck : 检查方式. PARAM_CHECK: varchar(1) 
	 */
	public String getParamCheck(){
		return this.paramCheck;	
	}
	
	/**
	 * Set paramRule : 检查规则. PARAM_RULE: varchar(100) 
	 */
	public void setParamRule(String paramRule){
		this.paramRule = paramRule;	
	}
	
	/**
	 * Get paramRule : 检查规则. PARAM_RULE: varchar(100) 
	 */
	public String getParamRule(){
		return this.paramRule;	
	}
	
	/**
	 * Set paramModifyFlag : 可修改标志. PARAM_MODIFY_FLAG: varchar(1) 
	 */
	public void setParamModifyFlag(String paramModifyFlag){
		this.paramModifyFlag = paramModifyFlag;	
	}
	
	/**
	 * Get paramModifyFlag : 可修改标志. PARAM_MODIFY_FLAG: varchar(1) 
	 */
	public String getParamModifyFlag(){
		return this.paramModifyFlag;	
	}
	
	/**
	 * Set paramDesc : 描述. PARAM_DESC: varchar(255) 
	 */
	public void setParamDesc(String paramDesc){
		this.paramDesc = paramDesc;	
	}
	
	/**
	 * Get paramDesc : 描述. PARAM_DESC: varchar(255) 
	 */
	public String getParamDesc(){
		return this.paramDesc;	
	}
	
	/**
	 * Set paramRemark : 注释. PARAM_REMARK: varchar(255) 
	 */
	public void setParamRemark(String paramRemark){
		this.paramRemark = paramRemark;	
	}
	
	/**
	 * Get paramRemark : 注释. PARAM_REMARK: varchar(255) 
	 */
	public String getParamRemark(){
		return this.paramRemark;	
	}
	
	/**
	 * Set lastUpdateOrg : 最后更新机构. LAST_UPDATE_ORG: varchar(30) 
	 */
	public void setLastUpdateOrg(String lastUpdateOrg){
		this.lastUpdateOrg = lastUpdateOrg;	
	}
	
	/**
	 * Get lastUpdateOrg : 最后更新机构. LAST_UPDATE_ORG: varchar(30) 
	 */
	public String getLastUpdateOrg(){
		return this.lastUpdateOrg;	
	}
	
	/**
	 * Set lastUpdateAcc : 最后更新账户. LAST_UPDATE_ACC: varchar(10) 
	 */
	public void setLastUpdateAcc(String lastUpdateAcc){
		this.lastUpdateAcc = lastUpdateAcc;	
	}
	
	/**
	 * Get lastUpdateAcc : 最后更新账户. LAST_UPDATE_ACC: varchar(10) 
	 */
	public String getLastUpdateAcc(){
		return this.lastUpdateAcc;	
	}
	
	/**
	 * Set lastUpdateTime : 最后更新时间. LAST_UPDATE_TIME: char(14) 
	 */
	public void setLastUpdateTime(String lastUpdateTime){
		this.lastUpdateTime = lastUpdateTime;	
	}
	
	/**
	 * Get lastUpdateTime : 最后更新时间. LAST_UPDATE_TIME: char(14) 
	 */
	public String getLastUpdateTime(){
		return this.lastUpdateTime;	
	}
	
	/**
	 * Set reserved1 : 保留域1. RESERVED1: VARCHAR(100) 
	 */
	public void setReserved1(String reserved1){
		this.reserved1 = reserved1;	
	}
	
	/**
	 * Get reserved1 : 保留域1. RESERVED1: VARCHAR(100) 
	 */
	public String getReserved1(){
		return this.reserved1;	
	}
	
	/**
	 * Set reserved2 : 保留域2. RESERVED2: VARCHAR(100) 
	 */
	public void setReserved2(String reserved2){
		this.reserved2 = reserved2;	
	}
	
	/**
	 * Get reserved2 : 保留域2. RESERVED2: VARCHAR(100) 
	 */
	public String getReserved2(){
		return this.reserved2;	
	}
	
}