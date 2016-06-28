package com.jiuyv.hhc.model.common ;

import java.util.Arrays;

// Generated by AutoCode4J
/**
 * EntityBean: 媒体附件表 : TBL_RES_MEDIA
 * This is a value object(VO). 
 * Description: 
 * 
 * @author AutoCode4J
 * @version 1.0
 */
public class ResMediaVo implements java.io.Serializable,IMedia {

	/** default Serial Version UID*/
	private static final long serialVersionUID = 1L;
	// -- Fields --
	/** medId : 文件ID. MED_ID: int */
	private Long medId ;
	
	/** medPath : 访问路径. MED_PATH: varchar(300) */
	private String medPath ;
	
	/** medDir : 目录. MED_DIR: varchar(250) */
	private String medDir ;
	
	/** medName : 文件名. MED_NAME: varchar(50) */
	private String medName ;
	
	/** mimeType : MIME类型. MIME_TYPE: varchar(50) */
	private String mimeType ;
	
	/** thumbnails : 预览内容. THUMBNAILS: mediumblob */
	private byte[] thumbnails ;
	
	/** medContent : 文件内容. MED_CONTENT: mediumblob */
	private byte[] medContent ;
	
	/** recCrtTime : 创建时间. REC_CRT_TIME: char(14) */
	private String recCrtTime ;
	
	/** recCrtAcc : 创建用户. REC_CRT_ACC: varchar(10) */
	private String recCrtAcc ;
	
	/** lastUptTime : 最后更新时间. LAST_UPT_TIME: char(14) */
	private String lastUptTime ;
	
	/** lastUptAcc : 最后更新账户. LAST_UPT_ACC: varchar(10) */
	private String lastUptAcc ;
	
	/** lastUptOrg : 最后更新机构. LAST_UPT_ORG: varchar(30) */
	private String lastUptOrg ;
	
	// -- Constructor --
	/**
	 * Constructor
	 * 
	 */
	public ResMediaVo() {
		// Default Construtor
	}
	
	public ResMediaVo(Long medId) {
		this.medId = medId;
	}
		
	// -- Setter And Getter
	/**
	 * Set medId : 文件ID. MED_ID: int 
	 */
	public void setMedId(Long medId){
		this.medId = medId;	
	}
	
	/**
	 * Get medId : 文件ID. MED_ID: int 
	 */
	public Long getMedId(){
		return this.medId;	
	}
	
	/**
	 * Set medPath : 访问路径. MED_PATH: varchar(300) 
	 */
	public void setMedPath(String medPath){
		this.medPath = medPath;	
	}
	
	/**
	 * Get medPath : 访问路径. MED_PATH: varchar(300) 
	 */
	public String getMedPath(){
		return this.medPath;	
	}
	
	/**
	 * Set medDir : 目录. MED_DIR: varchar(250) 
	 */
	public void setMedDir(String medDir){
		this.medDir = medDir;	
	}
	
	/**
	 * Get medDir : 目录. MED_DIR: varchar(250) 
	 */
	public String getMedDir(){
		return this.medDir;	
	}
	
	/**
	 * Set medName : 文件名. MED_NAME: varchar(50) 
	 */
	public void setMedName(String medName){
		this.medName = medName;	
	}
	
	/**
	 * Get medName : 文件名. MED_NAME: varchar(50) 
	 */
	public String getMedName(){
		return this.medName;	
	}
	
	/**
	 * Set mimeType : MIME类型. MIME_TYPE: varchar(50) 
	 */
	public void setMimeType(String mimeType){
		this.mimeType = mimeType;	
	}
	
	/**
	 * Get mimeType : MIME类型. MIME_TYPE: varchar(50) 
	 */
	public String getMimeType(){
		return this.mimeType;	
	}
	
	/**
	 * Set thumbnails : 预览内容. THUMBNAILS: mediumblob 
	 */
	public void setThumbnails(byte[] thumbnails){
		if (null != thumbnails) {
			this.thumbnails = Arrays.copyOf(thumbnails,
					thumbnails.length);
		} else {
			this.thumbnails = null;
		}
	}
	
	/**
	 * Get thumbnails : 预览内容. THUMBNAILS: mediumblob 
	 */
	public byte[] getThumbnails(){
		return this.thumbnails;	
	}
	
	/**
	 * Set medContent : 文件内容. MED_CONTENT: mediumblob 
	 */
	public void setMedContent(byte[] medContent){
		if (null != medContent) {
			this.medContent = Arrays.copyOf(medContent,
					medContent.length);
		} else {
			this.medContent = null;
		}
	}
	
	/**
	 * Get medContent : 文件内容. MED_CONTENT: mediumblob 
	 */
	public byte[] getMedContent(){
		return this.medContent;	
	}
	
	/**
	 * Set recCrtTime : 创建时间. REC_CRT_TIME: char(14) 
	 */
	public void setRecCrtTime(String recCrtTime){
		this.recCrtTime = recCrtTime;	
	}
	
	/**
	 * Get recCrtTime : 创建时间. REC_CRT_TIME: char(14) 
	 */
	public String getRecCrtTime(){
		return this.recCrtTime;	
	}
	
	/**
	 * Set recCrtAcc : 创建用户. REC_CRT_ACC: varchar(10) 
	 */
	public void setRecCrtAcc(String recCrtAcc){
		this.recCrtAcc = recCrtAcc;	
	}
	
	/**
	 * Get recCrtAcc : 创建用户. REC_CRT_ACC: varchar(10) 
	 */
	public String getRecCrtAcc(){
		return this.recCrtAcc;	
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

	@Override
	public String getContentType() {
		return this.getMimeType();
	}

	@Override
	public String getEncode() {
		return null;
	}

	@Override
	public byte[] getContent() {
		return this.thumbnails == null ? this.getMedContent() : this.thumbnails ;
	}
	
}