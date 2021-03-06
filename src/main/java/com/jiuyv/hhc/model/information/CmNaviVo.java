package com.jiuyv.hhc.model.information ;

// Generated by AutoCode4J
/**
 * EntityBean: 网站导航 : TBL_CM_NAVI
 * This is a value object(VO). 
 * Description: 
 * 
 * @author AutoCode4J
 * @version 1.0
 */
public class CmNaviVo implements java.io.Serializable {

	/** default Serial Version UID*/
	private static final long serialVersionUID = 1L;
	// -- Fields --
	/** naviSeq : 内部序号. NAVI_SEQ: NUMBER(18) */
	private Long naviSeq ;
	
	/** version : VERSION. VERSION: NUMBER(10) */
	private Long version ;
	
	/** naviStatus : 状态. NAVI_STATUS: VARCHAR2(2) */
	private String naviStatus ;
	
	/** isBlankTarget : 是否在新窗口中打开. IS_BLANK_TARGET: VARCHAR2(2) */
	private String isBlankTarget ;
	
	/** isVisible : 是否显示. IS_VISIBLE: VARCHAR2(2) */
	private String isVisible ;
	
	/** isHret : 是否为外链. IS_HRET: VARCHAR2(2) */
	private String isHret ;
	
	/** naviName : 名称. NAVI_NAME: VARCHAR2(255) */
	private String naviName ;
	
	/** naviTitle : 副标题. NAVI_TITLE: VARCHAR2(255) */
	private String naviTitle ;
	
	/** naviContent : 导航内容. NAVI_CONTENT: VARCHAR2(255) */
	private String naviContent;
	
	/** naviLogo : 导航LOGO. NAVI_LOGO: VARCHAR2(255) */
	private String naviLogo ;
	
	/** naviBanner : 导航BAN. NAVI_BANNER: VARCHAR2(255) */
	private String naviBanner ;
	
	/** naviStyle : 样式类别. NAVI_STYLE: VARCHAR2(255) */
	private String naviStyle ;
	
	/** naviPosition : 导航位置. NAVI_POSITION: VARCHAR2(2) */
	private String naviPosition ;
	
	/** naviOrderList : 导航顺序. NAVI_ORDER_LIST: NUMBER(10) */
	private Long naviOrderList ;
	
	/** naviUrl : 导航地址. NAVI_URL: VARCHAR2(255) */
	private String naviUrl ;
	
	/** recCrtTime : 记录创建时间. REC_CRT_TIME: CHAR(14) */
	private String recCrtTime ;
	
	/** recCrtAcc : 记录创建用户帐号. REC_CRT_ACC: VARCHAR2(30) */
	private String recCrtAcc ;
	
	/** lastUptOrg : 最后更新机构. LAST_UPT_ORG: VARCHAR2(30) */
	private String lastUptOrg ;
	
	/** lastUptTime : 最后修改时间. LAST_UPT_TIME: CHAR(14) */
	private String lastUptTime ;
	
	/** lastUptAcc : 最后修改用户帐号. LAST_UPT_ACC: VARCHAR2(30) */
	private String lastUptAcc ;
	
	private String naviStatusDesc;
	
	private String isVisibleDesc;
	
	private String isHretDesc;
	
	private String isBlankTargetDesc;
	
	private String naviPositionDesc;
	// -- Constructor --
	/**
	 * Constructor
	 * 
	 */
	public CmNaviVo() {
		// Default Construtor
	}
	
	public CmNaviVo(Long naviSeq) {
		this.naviSeq = naviSeq;
	}
		
	// -- Setter And Getter
	/**
	 * Set naviSeq : 内部序号. NAVI_SEQ: NUMBER(18) 
	 */
	public void setNaviSeq(Long naviSeq){
		this.naviSeq = naviSeq;	
	}
	
	/**
	 * Get naviSeq : 内部序号. NAVI_SEQ: NUMBER(18) 
	 */
	public Long getNaviSeq(){
		return this.naviSeq;	
	}
	
	/**
	 * Set version : VERSION. VERSION: NUMBER(10) 
	 */
	public void setVersion(Long version){
		this.version = version;	
	}
	
	/**
	 * Get version : VERSION. VERSION: NUMBER(10) 
	 */
	public Long getVersion(){
		return this.version;	
	}
	
	/**
	 * Set naviStatus : 状态. NAVI_STATUS: VARCHAR2(2) 
	 */
	public void setNaviStatus(String naviStatus){
		this.naviStatus = naviStatus;	
	}
	
	/**
	 * Get naviStatus : 状态. NAVI_STATUS: VARCHAR2(2) 
	 */
	public String getNaviStatus(){
		return this.naviStatus;	
	}
	
	/**
	 * Set isBlankTarget : 是否在新窗口中打开. IS_BLANK_TARGET: VARCHAR2(2) 
	 */
	public void setIsBlankTarget(String isBlankTarget){
		this.isBlankTarget = isBlankTarget;	
	}
	
	/**
	 * Get isBlankTarget : 是否在新窗口中打开. IS_BLANK_TARGET: VARCHAR2(2) 
	 */
	public String getIsBlankTarget(){
		return this.isBlankTarget;	
	}
	
	/**
	 * Set isVisible : 是否显示. IS_VISIBLE: VARCHAR2(2) 
	 */
	public void setIsVisible(String isVisible){
		this.isVisible = isVisible;	
	}
	
	/**
	 * Get isVisible : 是否显示. IS_VISIBLE: VARCHAR2(2) 
	 */
	public String getIsVisible(){
		return this.isVisible;	
	}
	
	/**
	 * Set isHret : 是否为外链. IS_HRET: VARCHAR2(2) 
	 */
	public void setIsHret(String isHret){
		this.isHret = isHret;	
	}
	
	/**
	 * Get isHret : 是否为外链. IS_HRET: VARCHAR2(2) 
	 */
	public String getIsHret(){
		return this.isHret;	
	}
	
	/**
	 * Set naviName : 名称. NAVI_NAME: VARCHAR2(255) 
	 */
	public void setNaviName(String naviName){
		this.naviName = naviName;	
	}
	
	/**
	 * Get naviName : 名称. NAVI_NAME: VARCHAR2(255) 
	 */
	public String getNaviName(){
		return this.naviName;	
	}
	
	/**
	 * Set naviStyle : 样式类别. NAVI_STYLE: VARCHAR2(255) 
	 */
	public void setNaviStyle(String naviStyle){
		this.naviStyle = naviStyle;	
	}
	
	/**
	 * Get naviStyle : 样式类别. NAVI_STYLE: VARCHAR2(255) 
	 */
	public String getNaviStyle(){
		return this.naviStyle;	
	}
	
	/**
	 * Set naviPosition : 导航位置. NAVI_POSITION: VARCHAR2(2) 
	 */
	public void setNaviPosition(String naviPosition){
		this.naviPosition = naviPosition;	
	}
	
	/**
	 * Get naviPosition : 导航位置. NAVI_POSITION: VARCHAR2(2) 
	 */
	public String getNaviPosition(){
		return this.naviPosition;	
	}
	
	/**
	 * Set naviOrderList : 导航顺序. NAVI_ORDER_LIST: NUMBER(10) 
	 */
	public void setNaviOrderList(Long naviOrderList){
		this.naviOrderList = naviOrderList;	
	}
	
	/**
	 * Get naviOrderList : 导航顺序. NAVI_ORDER_LIST: NUMBER(10) 
	 */
	public Long getNaviOrderList(){
		return this.naviOrderList;	
	}
	
	/**
	 * Set naviUrl : 导航地址. NAVI_URL: VARCHAR2(255) 
	 */
	public void setNaviUrl(String naviUrl){
		this.naviUrl = naviUrl;	
	}
	
	/**
	 * Get naviUrl : 导航地址. NAVI_URL: VARCHAR2(255) 
	 */
	public String getNaviUrl(){
		return this.naviUrl;	
	}
	
	/**
	 * Set recCrtTime : 记录创建时间. REC_CRT_TIME: CHAR(14) 
	 */
	public void setRecCrtTime(String recCrtTime){
		this.recCrtTime = recCrtTime;	
	}
	
	/**
	 * Get recCrtTime : 记录创建时间. REC_CRT_TIME: CHAR(14) 
	 */
	public String getRecCrtTime(){
		return this.recCrtTime;	
	}
	
	/**
	 * Set recCrtAcc : 记录创建用户帐号. REC_CRT_ACC: VARCHAR2(30) 
	 */
	public void setRecCrtAcc(String recCrtAcc){
		this.recCrtAcc = recCrtAcc;	
	}
	
	/**
	 * Get recCrtAcc : 记录创建用户帐号. REC_CRT_ACC: VARCHAR2(30) 
	 */
	public String getRecCrtAcc(){
		return this.recCrtAcc;	
	}
	
	/**
	 * Set lastUptOrg : 最后更新机构. LAST_UPT_ORG: VARCHAR2(30) 
	 */
	public void setLastUptOrg(String lastUptOrg){
		this.lastUptOrg = lastUptOrg;	
	}
	
	/**
	 * Get lastUptOrg : 最后更新机构. LAST_UPT_ORG: VARCHAR2(30) 
	 */
	public String getLastUptOrg(){
		return this.lastUptOrg;	
	}
	
	/**
	 * Set lastUptTime : 最后修改时间. LAST_UPT_TIME: CHAR(14) 
	 */
	public void setLastUptTime(String lastUptTime){
		this.lastUptTime = lastUptTime;	
	}
	
	/**
	 * Get lastUptTime : 最后修改时间. LAST_UPT_TIME: CHAR(14) 
	 */
	public String getLastUptTime(){
		return this.lastUptTime;	
	}
	
	/**
	 * Set lastUptAcc : 最后修改用户帐号. LAST_UPT_ACC: VARCHAR2(30) 
	 */
	public void setLastUptAcc(String lastUptAcc){
		this.lastUptAcc = lastUptAcc;	
	}
	
	/**
	 * Get lastUptAcc : 最后修改用户帐号. LAST_UPT_ACC: VARCHAR2(30) 
	 */
	public String getLastUptAcc(){
		return this.lastUptAcc;	
	}

	public String getNaviTitle() {
		return naviTitle;
	}

	public void setNaviTitle(String naviTitle) {
		this.naviTitle = naviTitle;
	}

	public String getNaviLogo() {
		return naviLogo;
	}

	public void setNaviLogo(String naviLogo) {
		this.naviLogo = naviLogo;
	}

	public String getNaviBanner() {
		return naviBanner;
	}

	public void setNaviBanner(String naviBanner) {
		this.naviBanner = naviBanner;
	}

	public String getNaviStatusDesc() {
		return naviStatusDesc;
	}

	public void setNaviStatusDesc(String naviStatusDesc) {
		this.naviStatusDesc = naviStatusDesc;
	}

	public String getIsVisibleDesc() {
		return isVisibleDesc;
	}

	public void setIsVisibleDesc(String isVisibleDesc) {
		this.isVisibleDesc = isVisibleDesc;
	}

	public String getIsHretDesc() {
		return isHretDesc;
	}

	public void setIsHretDesc(String isHretDesc) {
		this.isHretDesc = isHretDesc;
	}

	public String getIsBlankTargetDesc() {
		return isBlankTargetDesc;
	}

	public void setIsBlankTargetDesc(String isBlankTargetDesc) {
		this.isBlankTargetDesc = isBlankTargetDesc;
	}

	public String getNaviPositionDesc() {
		return naviPositionDesc;
	}

	public void setNaviPositionDesc(String naviPositionDesc) {
		this.naviPositionDesc = naviPositionDesc;
	}

	public String getNaviContent() {
		return naviContent;
	}

	public void setNaviContent(String naviContent) {
		this.naviContent = naviContent;
	}
	
}