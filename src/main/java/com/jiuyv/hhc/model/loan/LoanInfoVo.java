package com.jiuyv.hhc.model.loan ;

// Generated by AutoCode4J
/**
 * EntityBean: 商户贷款信息表 : TBL_LOAN_INFO
 * This is a value object(VO). 
 * Description: 
 * 
 * @author AutoCode4J
 * @version 1.0
 */
@Deprecated
public class LoanInfoVo implements java.io.Serializable {

	/** default Serial Version UID*/
	private static final long serialVersionUID = 1L;
	// -- Fields --
	/** loanId : 贷款编号. LOAN_ID: VARCHAR(32) */
	private String loanId ;
	
	/** cupLoanId : 银联贷款编号. CUP_LOAN_ID: VARCHAR2(40)  */
	private String cupLoanId ;
	
	/** version : VERSION. VERSION: NUMERIC(10) */
	private Long version ;
	
	/** mchntCode : 内部商户号. MCHNT_CODE: NUMBER(10) */
	private Long mchntCode ;
	
	/** licNo : 营业执照号码. LIC_NO: VARCHAR(70) */
	private String licNo ;
	
	/** reveRegCd : 税务登记代码. REVE_REG_CD: VARCHAR(70) */
	private String reveRegCd ;
	
	/** artifNm : 法人代表姓名. ARTIF_NM: VARCHAR(40) */
	private String artifNm ;
	
	/** artifCertifId : 法人代表证件号. ARTIF_CERTIF_ID: VARCHAR(22) */
	private String artifCertifId ;
	
	/** artifCertifType : 法人代表证件类型. ARTIF_CERTIF_TYPE: VARCHAR(2) */
	private String artifCertifType ;
	
	/** creditSt : 融资信贷状态. CREDIT_ST: VARCHAR(2) */
	private String creditSt ;
	
	/** mchntSt : 商户状态. MCHNT_ST: VARCHAR(2) */
	private String mchntSt ;
	
	/** lenderId : 资金方编码. LENDER_ID: VARCHAR(100) */
	private String lenderId ;
	
	/** loanAt : 贷款金额. LOAN_AT: NUMBER(11) */
	private Long loanAt ;
	
	/** loanCycle : 贷款周期. LOAN_CYCLE: NUMBER(2) */
	private Long loanCycle ;
	
	/** loanrt : 贷款利率. LOANRT: NUMBER(4) */
	private Long loanrt ;
	
	/** cardCreditLimit : 银行卡授信额度. CARD_CREDIT_LIMIT: NUMBER(11) */
	private Long cardCreditLimit ;
	
	/** cardUseLimit : 银行卡使用额度. CARD_USE_LIMIT: NUMBER(11) */
	private Long cardUseLimit ;
	
	/** cardExpire : 银行卡有效期. CARD_EXPIRE: CHAR(4) */
	private String cardExpire ;
	
	/** loanSuccTm : 放款成功时间. LOAN_SUCC_TM: CHAR(14) */
	private String loanSuccTm ;
	
	/** loanBalance : 贷款余额. LOAN_BALANCE: NUMBER(11) */
	private Long loanBalance ;
	
	/** loanProd : 贷款产品. LOAN_PROD: VARCHAR(8) */
	private String loanProd ;
	
	/** svcFeeBenefit : 服务费收益. SVC_FEE_BENEFIT: NUMBER(11) */
	private Long svcFeeBenefit ;
	
	/** allotTp : 分润方式. ALLOT_TP: VARCHAR(20) */
	private String allotTp ;
	
	/** exceedAt : 逾期金额. EXCEED_AT: NUMBER(11) */
	private Long exceedAt ;
	
	/** exceedNum : 逾期笔数. EXCEED_NUM: NUMBER(11) */
	private Long exceedNum ;
	
	/** loanExpire : 贷款有效期. LOAN_EXPIRE: VARCHAR(8) */
	private String loanExpire ;
	
	/** loanSt : 贷款状态. LOAN_ST: VARCHAR(2) */
	private String loanSt ;
	
	/** remark : 备注. REMARK: VARCHAR(200) */
	private String remark ;
	
	/** recCrtTime : 记录创建时间. REC_CRT_TIME: CHAR(14) */
	private String recCrtTime ;
	
	/** lastUptOrg : 最后更新机构. LAST_UPT_ORG: VARCHAR2(30) */
	private String lastUptOrg ;
	
	/** lastUptTime : 最后修改时间. LAST_UPT_TIME: CHAR(14) */
	private String lastUptTime ;
	
	/** lastUptAcc : 最后修改用户帐号. LAST_UPT_ACC: VARCHAR2(30) */
	private String lastUptAcc ;
	
	private String customerCode;
	// extended 
	
	private String loanStDesc;
	
	/** mchntCd : 商户代码. MCHNT_CD: VARCHAR(15) */
	private String mchntCd ;
	
	/** mchntCnName : 商户中文名称. MCHNT_CN_NAME: VARCHAR(100) */
	private String mchntCnName ;
	
	// -- Constructor --
	/**
	 * Constructor
	 * 
	 */
	public LoanInfoVo() {
		// Default Construtor
	}
	
	public LoanInfoVo(String loanId) {
		this.loanId = loanId;
	}
		
	// -- Setter And Getter
	/**
	 * Set loanId : 贷款编号. LOAN_ID: VARCHAR(32) 
	 */
	public void setLoanId(String loanId){
		this.loanId = loanId;	
	}
	
	/**
	 * Get loanId : 贷款编号. LOAN_ID: VARCHAR(32) 
	 */
	public String getLoanId(){
		return this.loanId;	
	}
	
	/**
	 * Set version : VERSION. VERSION: NUMERIC(10) 
	 */
	public void setVersion(Long version){
		this.version = version;	
	}
	
	/**
	 * Get version : VERSION. VERSION: NUMERIC(10) 
	 */
	public Long getVersion(){
		return this.version;	
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
	 * Set licNo : 营业执照号码. LIC_NO: VARCHAR(70) 
	 */
	public void setLicNo(String licNo){
		this.licNo = licNo;	
	}
	
	/**
	 * Get licNo : 营业执照号码. LIC_NO: VARCHAR(70) 
	 */
	public String getLicNo(){
		return this.licNo;	
	}
	
	/**
	 * Set reveRegCd : 税务登记代码. REVE_REG_CD: VARCHAR(70) 
	 */
	public void setReveRegCd(String reveRegCd){
		this.reveRegCd = reveRegCd;	
	}
	
	/**
	 * Get reveRegCd : 税务登记代码. REVE_REG_CD: VARCHAR(70) 
	 */
	public String getReveRegCd(){
		return this.reveRegCd;	
	}
	
	/**
	 * Set artifNm : 法人代表姓名. ARTIF_NM: VARCHAR(40) 
	 */
	public void setArtifNm(String artifNm){
		this.artifNm = artifNm;	
	}
	
	/**
	 * Get artifNm : 法人代表姓名. ARTIF_NM: VARCHAR(40) 
	 */
	public String getArtifNm(){
		return this.artifNm;	
	}
	
	/**
	 * Set artifCertifId : 法人代表证件号. ARTIF_CERTIF_ID: VARCHAR(22) 
	 */
	public void setArtifCertifId(String artifCertifId){
		this.artifCertifId = artifCertifId;	
	}
	
	/**
	 * Get artifCertifId : 法人代表证件号. ARTIF_CERTIF_ID: VARCHAR(22) 
	 */
	public String getArtifCertifId(){
		return this.artifCertifId;	
	}
	
	/**
	 * Set artifCertifType : 法人代表证件类型. ARTIF_CERTIF_TYPE: VARCHAR(2) 
	 */
	public void setArtifCertifType(String artifCertifType){
		this.artifCertifType = artifCertifType;	
	}
	
	/**
	 * Get artifCertifType : 法人代表证件类型. ARTIF_CERTIF_TYPE: VARCHAR(2) 
	 */
	public String getArtifCertifType(){
		return this.artifCertifType;	
	}
	
	/**
	 * Set creditSt : 融资信贷状态. CREDIT_ST: VARCHAR(2) 
	 */
	public void setCreditSt(String creditSt){
		this.creditSt = creditSt;	
	}
	
	/**
	 * Get creditSt : 融资信贷状态. CREDIT_ST: VARCHAR(2) 
	 */
	public String getCreditSt(){
		return this.creditSt;	
	}
	
	/**
	 * Set mchntSt : 商户状态. MCHNT_ST: VARCHAR(2) 
	 */
	public void setMchntSt(String mchntSt){
		this.mchntSt = mchntSt;	
	}
	
	/**
	 * Get mchntSt : 商户状态. MCHNT_ST: VARCHAR(2) 
	 */
	public String getMchntSt(){
		return this.mchntSt;	
	}
	
	/**
	 * Set lenderId : 资金方编码. LENDER_ID: VARCHAR(100) 
	 */
	public void setLenderId(String lenderId){
		this.lenderId = lenderId;	
	}
	
	/**
	 * Get lenderId : 资金方编码. LENDER_ID: VARCHAR(100) 
	 */
	public String getLenderId(){
		return this.lenderId;	
	}
	
	/**
	 * Set loanAt : 贷款金额. LOAN_AT: NUMBER(11) 
	 */
	public void setLoanAt(Long loanAt){
		this.loanAt = loanAt;	
	}
	
	/**
	 * Get loanAt : 贷款金额. LOAN_AT: NUMBER(11) 
	 */
	public Long getLoanAt(){
		return this.loanAt;	
	}
	
	/**
	 * Set loanCycle : 贷款周期. LOAN_CYCLE: NUMBER(2) 
	 */
	public void setLoanCycle(Long loanCycle){
		this.loanCycle = loanCycle;	
	}
	
	/**
	 * Get loanCycle : 贷款周期. LOAN_CYCLE: NUMBER(2) 
	 */
	public Long getLoanCycle(){
		return this.loanCycle;	
	}
	
	/**
	 * Set loanrt : 贷款利率. LOANRT: NUMBER(4) 
	 */
	public void setLoanrt(Long loanrt){
		this.loanrt = loanrt;	
	}
	
	/**
	 * Get loanrt : 贷款利率. LOANRT: NUMBER(4) 
	 */
	public Long getLoanrt(){
		return this.loanrt;	
	}
	
	/**
	 * Set cardCreditLimit : 银行卡授信额度. CARD_CREDIT_LIMIT: NUMBER(11) 
	 */
	public void setCardCreditLimit(Long cardCreditLimit){
		this.cardCreditLimit = cardCreditLimit;	
	}
	
	/**
	 * Get cardCreditLimit : 银行卡授信额度. CARD_CREDIT_LIMIT: NUMBER(11) 
	 */
	public Long getCardCreditLimit(){
		return this.cardCreditLimit;	
	}
	
	/**
	 * Set cardUseLimit : 银行卡使用额度. CARD_USE_LIMIT: NUMBER(11) 
	 */
	public void setCardUseLimit(Long cardUseLimit){
		this.cardUseLimit = cardUseLimit;	
	}
	
	/**
	 * Get cardUseLimit : 银行卡使用额度. CARD_USE_LIMIT: NUMBER(11) 
	 */
	public Long getCardUseLimit(){
		return this.cardUseLimit;	
	}
	
	/**
	 * Set cardExpire : 银行卡有效期. CARD_EXPIRE: CHAR(4) 
	 */
	public void setCardExpire(String cardExpire){
		this.cardExpire = cardExpire;	
	}
	
	/**
	 * Get cardExpire : 银行卡有效期. CARD_EXPIRE: CHAR(4) 
	 */
	public String getCardExpire(){
		return this.cardExpire;	
	}
	
	/**
	 * Set loanSuccTm : 放款成功时间. LOAN_SUCC_TM: CHAR(14) 
	 */
	public void setLoanSuccTm(String loanSuccTm){
		this.loanSuccTm = loanSuccTm;	
	}
	
	/**
	 * Get loanSuccTm : 放款成功时间. LOAN_SUCC_TM: CHAR(14) 
	 */
	public String getLoanSuccTm(){
		return this.loanSuccTm;	
	}
	
	/**
	 * Set loanBalance : 贷款余额. LOAN_BALANCE: NUMBER(11) 
	 */
	public void setLoanBalance(Long loanBalance){
		this.loanBalance = loanBalance;	
	}
	
	/**
	 * Get loanBalance : 贷款余额. LOAN_BALANCE: NUMBER(11) 
	 */
	public Long getLoanBalance(){
		return this.loanBalance;	
	}
	
	/**
	 * Set loanProd : 贷款产品. LOAN_PROD: VARCHAR(8) 
	 */
	public void setLoanProd(String loanProd){
		this.loanProd = loanProd;	
	}
	
	/**
	 * Get loanProd : 贷款产品. LOAN_PROD: VARCHAR(8) 
	 */
	public String getLoanProd(){
		return this.loanProd;	
	}
	
	/**
	 * Set svcFeeBenefit : 服务费收益. SVC_FEE_BENEFIT: NUMBER(11) 
	 */
	public void setSvcFeeBenefit(Long svcFeeBenefit){
		this.svcFeeBenefit = svcFeeBenefit;	
	}
	
	/**
	 * Get svcFeeBenefit : 服务费收益. SVC_FEE_BENEFIT: NUMBER(11) 
	 */
	public Long getSvcFeeBenefit(){
		return this.svcFeeBenefit;	
	}
	
	/**
	 * Set allotTp : 分润方式. ALLOT_TP: VARCHAR(20) 
	 */
	public void setAllotTp(String allotTp){
		this.allotTp = allotTp;	
	}
	
	/**
	 * Get allotTp : 分润方式. ALLOT_TP: VARCHAR(20) 
	 */
	public String getAllotTp(){
		return this.allotTp;	
	}
	
	/**
	 * Set exceedAt : 逾期金额. EXCEED_AT: NUMBER(11) 
	 */
	public void setExceedAt(Long exceedAt){
		this.exceedAt = exceedAt;	
	}
	
	/**
	 * Get exceedAt : 逾期金额. EXCEED_AT: NUMBER(11) 
	 */
	public Long getExceedAt(){
		return this.exceedAt;	
	}
	
	/**
	 * Set exceedNum : 逾期笔数. EXCEED_NUM: NUMBER(11) 
	 */
	public void setExceedNum(Long exceedNum){
		this.exceedNum = exceedNum;	
	}
	
	/**
	 * Get exceedNum : 逾期笔数. EXCEED_NUM: NUMBER(11) 
	 */
	public Long getExceedNum(){
		return this.exceedNum;	
	}
	
	/**
	 * Set loanExpire : 贷款有效期. LOAN_EXPIRE: VARCHAR(8) 
	 */
	public void setLoanExpire(String loanExpire){
		this.loanExpire = loanExpire;	
	}
	
	/**
	 * Get loanExpire : 贷款有效期. LOAN_EXPIRE: VARCHAR(8) 
	 */
	public String getLoanExpire(){
		return this.loanExpire;	
	}
	
	/**
	 * Set loanSt : 贷款状态. LOAN_ST: VARCHAR(2) 
	 */
	public void setLoanSt(String loanSt){
		this.loanSt = loanSt;	
	}
	
	/**
	 * Get loanSt : 贷款状态. LOAN_ST: VARCHAR(2) 
	 */
	public String getLoanSt(){
		return this.loanSt;	
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

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getLoanStDesc() {
		return loanStDesc;
	}

	public void setLoanStDesc(String loanStDesc) {
		this.loanStDesc = loanStDesc;
	}

	/**
	 * @return the cupLoanId
	 */
	public String getCupLoanId() {
		return cupLoanId;
	}

	/**
	 * @param cupLoanId the cupLoanId to set
	 */
	public void setCupLoanId(String cupLoanId) {
		this.cupLoanId = cupLoanId;
	}

	/**
	 * @return the mchntCd
	 */
	public String getMchntCd() {
		return mchntCd;
	}

	/**
	 * @param mchntCd the mchntCd to set
	 */
	public void setMchntCd(String mchntCd) {
		this.mchntCd = mchntCd;
	}

	/**
	 * @return the mchntCnName
	 */
	public String getMchntCnName() {
		return mchntCnName;
	}

	/**
	 * @param mchntCnName the mchntCnName to set
	 */
	public void setMchntCnName(String mchntCnName) {
		this.mchntCnName = mchntCnName;
	}
	
}