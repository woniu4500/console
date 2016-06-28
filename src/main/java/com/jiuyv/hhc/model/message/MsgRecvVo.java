package com.jiuyv.hhc.model.message ;

// Generated by AutoCode4J
/**
 * EntityBean: 系统消息接收方 : TBL_MSG_RECV
 * This is a value object(VO). 
 * Description: 
 * 
 * @author AutoCode4J
 * @version 1.0
 */
public class MsgRecvVo implements java.io.Serializable {

	/** default Serial Version UID*/
	private static final long serialVersionUID = 1L;
	// -- Fields --
	/** msgId : 消息ID. MSG_ID: int */
	private Long msgId ;
	
	/** recvId : 收件人ID. RECV_ID: int */
	private Long recvId ;
	
	/** sendId : 发件人ID. SEND_ID: int */
	private Long sendId ;
	
	/** readFlag : 信件状态. READ_FLAG: varchar(1) */
	private String readFlag ;
	// -- Extend --
	/** 收件人姓名 */
	private String recvName ;
	/** 收件人帐号 */
	private String recvAcct ; 
	/** readFlag : 信件状态. READ_FLAG: varchar(1) */
	private String readFlagDesc;
	// -- Constructor --
	/**
	 * Constructor
	 * 
	 */
	public MsgRecvVo() {
		// Default Construtor
	}
	
	public MsgRecvVo(Long msgId, Long recvId) {
		this.msgId = msgId;
		this.recvId = recvId;
	}
		
	// -- Setter And Getter
	/**
	 * Set msgId : 消息ID. MSG_ID: int 
	 */
	public void setMsgId(Long msgId){
		this.msgId = msgId;	
	}
	
	/**
	 * Get msgId : 消息ID. MSG_ID: int 
	 */
	public Long getMsgId(){
		return this.msgId;	
	}
	
	/**
	 * Set recvId : 收件人ID. RECV_ID: int 
	 */
	public void setRecvId(Long recvId){
		this.recvId = recvId;	
	}
	
	/**
	 * Get recvId : 收件人ID. RECV_ID: int 
	 */
	public Long getRecvId(){
		return this.recvId;	
	}
	
	/**
	 * Set sendId : 发件人ID. SEND_ID: int 
	 */
	public void setSendId(Long sendId){
		this.sendId = sendId;	
	}
	
	/**
	 * Get sendId : 发件人ID. SEND_ID: int 
	 */
	public Long getSendId(){
		return this.sendId;	
	}
	
	/**
	 * Set readFlag : 信件状态. READ_FLAG: varchar(1) 
	 */
	public void setReadFlag(String readFlag){
		this.readFlag = readFlag;	
	}
	
	/**
	 * Get readFlag : 信件状态. READ_FLAG: varchar(1) 
	 */
	public String getReadFlag(){
		return this.readFlag;	
	}

	public String getReadFlagDesc() {
		return readFlagDesc;
	}

	public void setReadFlagDesc(String readFlagDesc) {
		this.readFlagDesc = readFlagDesc;
	}

	public String getRecvName() {
		return recvName;
	}

	public void setRecvName(String recvName) {
		this.recvName = recvName;
	}

	public String getRecvAcct() {
		return recvAcct;
	}

	public void setRecvAcct(String recvAcct) {
		this.recvAcct = recvAcct;
	}
	
}