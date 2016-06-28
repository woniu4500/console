package com.jiuyv.hhc.model.loan ;

// Generated by AutoCode4J
/**
 * EntityBean: 商户风险统计信息 : TBL_MHT_RISK_DATA
 * This is a value object(VO). 
 * Description: 
 * 
 * @author AutoCode4J
 * @version 1.0
 */
public class MhtRiskDataVo implements java.io.Serializable {

	/** default Serial Version UID*/
	private static final long serialVersionUID = 1L;
	// -- Fields --
	/** riskId : 风险数据号. RISK_ID: NUMBER(10) */
	private Long riskId ;
	
	/** mchntCode : 内部商户号. MCHNT_CODE: NUMBER(10) */
	private Long mchntCode ;
	
	/** fraudTransNum : 近三个月欺诈交易总笔数. FRAUD_TRANS_NUM: NUMBER(12) */
	private Long fraudTransNum ;
	
	/** fraudTransAt : 近三个月欺诈交易总金额. FRAUD_TRANS_AT: NUMBER(12) */
	private Long fraudTransAt ;
	
	/** susMchntIn : 可疑商户名单是否命中. SUS_MCHNT_IN: VARCHAR(1) */
	private String susMchntIn ;
	
	/** negCdhdIn : 不良持卡人名单是否命中. NEG_CDHD_IN: VARCHAR(1) */
	private String negCdhdIn ;
	
	/** remark : 备注. REMARK: VARCHAR(200) */
	private String remark ;
	
	/** recTime : 创建时间. REC_TIME: CHAR(14) */
	private String recTime ;
	
	private String susMchntInDesc;
	
	private String negCdhdInDesc;
	// -- Constructor --
	/**
	 * Constructor
	 * 
	 */
	public MhtRiskDataVo() {
		// Default Construtor
	}
	
	public MhtRiskDataVo(Long riskId) {
		this.riskId = riskId;
	}
		
	// -- Setter And Getter
	/**
	 * Set riskId : 风险数据号. RISK_ID: NUMBER(10) 
	 */
	public void setRiskId(Long riskId){
		this.riskId = riskId;	
	}
	
	/**
	 * Get riskId : 风险数据号. RISK_ID: NUMBER(10) 
	 */
	public Long getRiskId(){
		return this.riskId;	
	}
	
	/**
	 * Set mchntCode : 内部商户号. MCHNT_CODE: NUMBER(10) 
	 */
	public void setMchntCode(Long mchntCode){
		this.mchntCode = mchntCode;	
	}
	
	/**
	 * Get mchntCode : 内部商户号. MCHNT_CODE: NUMBER(10) 
	 */
	public Long getMchntCode(){
		return this.mchntCode;	
	}
	
	/**
	 * Set fraudTransNum : 近三个月欺诈交易总笔数. FRAUD_TRANS_NUM: NUMBER(12) 
	 */
	public void setFraudTransNum(Long fraudTransNum){
		this.fraudTransNum = fraudTransNum;	
	}
	
	/**
	 * Get fraudTransNum : 近三个月欺诈交易总笔数. FRAUD_TRANS_NUM: NUMBER(12) 
	 */
	public Long getFraudTransNum(){
		return this.fraudTransNum;	
	}
	
	/**
	 * Set fraudTransAt : 近三个月欺诈交易总金额. FRAUD_TRANS_AT: NUMBER(12) 
	 */
	public void setFraudTransAt(Long fraudTransAt){
		this.fraudTransAt = fraudTransAt;	
	}
	
	/**
	 * Get fraudTransAt : 近三个月欺诈交易总金额. FRAUD_TRANS_AT: NUMBER(12) 
	 */
	public Long getFraudTransAt(){
		return this.fraudTransAt;	
	}
	
	/**
	 * Set susMchntIn : 可疑商户名单是否命中. SUS_MCHNT_IN: VARCHAR(1) 
	 */
	public void setSusMchntIn(String susMchntIn){
		this.susMchntIn = susMchntIn;	
	}
	
	/**
	 * Get susMchntIn : 可疑商户名单是否命中. SUS_MCHNT_IN: VARCHAR(1) 
	 */
	public String getSusMchntIn(){
		return this.susMchntIn;	
	}
	
	/**
	 * Set negCdhdIn : 不良持卡人名单是否命中. NEG_CDHD_IN: VARCHAR(1) 
	 */
	public void setNegCdhdIn(String negCdhdIn){
		this.negCdhdIn = negCdhdIn;	
	}
	
	/**
	 * Get negCdhdIn : 不良持卡人名单是否命中. NEG_CDHD_IN: VARCHAR(1) 
	 */
	public String getNegCdhdIn(){
		return this.negCdhdIn;	
	}
	
	/**
	 * Set remark : 备注. REMARK: VARCHAR(200) 
	 */
	public void setRemark(String remark){
		this.remark = remark;	
	}
	
	/**
	 * Get remark : 备注. REMARK: VARCHAR(200) 
	 */
	public String getRemark(){
		return this.remark;	
	}
	
	/**
	 * Set recTime : 创建时间. REC_TIME: CHAR(14) 
	 */
	public void setRecTime(String recTime){
		this.recTime = recTime;	
	}
	
	/**
	 * Get recTime : 创建时间. REC_TIME: CHAR(14) 
	 */
	public String getRecTime(){
		return this.recTime;	
	}

	public String getSusMchntInDesc() {
		return susMchntInDesc;
	}

	public void setSusMchntInDesc(String susMchntInDesc) {
		this.susMchntInDesc = susMchntInDesc;
	}

	public String getNegCdhdInDesc() {
		return negCdhdInDesc;
	}

	public void setNegCdhdInDesc(String negCdhdInDesc) {
		this.negCdhdInDesc = negCdhdInDesc;
	}
	
}