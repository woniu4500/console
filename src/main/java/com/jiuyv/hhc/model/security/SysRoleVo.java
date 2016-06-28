package com.jiuyv.hhc.model.security ;

// Generated by AutoCode4J
/**
 * EntityBean: 控制台角色表 : TBL_SYS_ROLE
 * This is a value object(VO). 
 * Description: 
 * 
 * @author AutoCode4J
 * @version 1.0
 */
public class SysRoleVo implements java.io.Serializable {

	/** default Serial Version UID*/
	private static final long serialVersionUID = 1L;
	// -- Fields --
	/** roleId : 角色ID. ROLE_ID: int */
	private Long roleId ;
	
	/** version : VERSION. VERSION: int */
	private Long version ;
	
	/** roleName : 角色名. ROLE_NAME: varchar(60) */
	private String roleName ;
	
	/** orgCode : 机构代码. ORG_CODE: varchar(30) */
	private String orgCode ;
	
	/** status : 状态. STATUS: char(1) */
	private String status ;
	
	/** lastUptTime : 最后更新时间. LAST_UPT_TIME: char(14) */
	private String lastUptTime ;
	
	/** lastUptAcc : 最后更新账户. LAST_UPT_ACC: varchar(10) */
	private String lastUptAcc ;
	
	/** lastUptOrg : 最后更新机构. LAST_UPT_ORG: varchar(30) */
	private String lastUptOrg ;
	
	/** 是否勾选 */
	private boolean checked ; 
	
	// -- Extended --
	/** 机构名称  */
	private String orgName ; 
	/** 状态 */
	private String statusDesc;
	// -- Constructor --
	/**
	 * Constructor
	 * 
	 */
	public SysRoleVo() {
		// Default Construtor
	}
	
	public SysRoleVo(Long roleId) {
		this.roleId = roleId;
	}
		
	// -- Setter And Getter
	/**
	 * Set roleId : 角色ID. ROLE_ID: int 
	 */
	public void setRoleId(Long roleId){
		this.roleId = roleId;	
	}
	
	/**
	 * Get roleId : 角色ID. ROLE_ID: int 
	 */
	public Long getRoleId(){
		return this.roleId;	
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
	 * Set roleName : 角色名. ROLE_NAME: varchar(60) 
	 */
	public void setRoleName(String roleName){
		this.roleName = roleName;	
	}
	
	/**
	 * Get roleName : 角色名. ROLE_NAME: varchar(60) 
	 */
	public String getRoleName(){
		return this.roleName;	
	}
	
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

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	// extend for flexigrid
	/**
	 * 
	 * @return
	 */
	public boolean isCheck() {
		return checked ; 
	}
}