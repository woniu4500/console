package com.jiuyv.hhc.console.message.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.common.excel.CellDataRender;
import com.jiuyv.common.excel.CellDataType;
import com.jiuyv.common.template.StringTemplateViewer;
import com.jiuyv.hhc.console.basic.action.DefaultPageSupportAction;
import com.jiuyv.hhc.console.common.service.ISysParamService;
import com.jiuyv.hhc.console.message.service.IMailService;
import com.jiuyv.hhc.console.message.service.ITplMngrService;
import com.jiuyv.hhc.model.ErrorCode;
import com.jiuyv.hhc.model.common.SysParamVo;
import com.jiuyv.hhc.model.message.MsgMailVo;
import com.jiuyv.hhc.model.message.MsgTplVo;

/**
 * The Class TplMngrAction.
 * 模板管理 Action

 * @author 
 * @since 2014-2-21 11:37:59
 * @version 1.0.0
 */
public class TplMngrAction extends DefaultPageSupportAction {

	/** 模板管理Service. */
	private ITplMngrService tplMngrService ;
	
	/** 邮件Service. */ 
	private IMailService mailService ; 
	
	/** 邮件Service. */ 
	private ISysParamService sysParamService ; 
	
	/** Constant String TPL_FN: String :. */
	private static final String TPL_FN = "模板列表";
	
	/** Constant String FLD_TPL_ID: String :. */
	private static final String FLD_TPL_ID = "tplId";
	
	/** 发送方信息列. */
	private static List<CellDataType> tplCol = new ArrayList<CellDataType>(); 
	static {
		tplCol.add(new CellDataType("tplId", "ID"));
		tplCol.add(new CellDataType("tplName", "模版名称"));
		tplCol.add(new CellDataType("tplRemark", "备注"));
		tplCol.add(new CellDataType("lstUpdDttm", "修改时间",CellDataRender.time));
		tplCol.add(new CellDataType("lstUpdUserId", "修改帐号"));
	}
	/**
	 * Find tpl page.
	 * 分页查询模板
	 * @return the string
	 * @throws Exception the exception
	 */
	public String findTplPage() throws Exception {
		return resultData(tplMngrService.findTplPage(getFilters(),getPageInfo(),getUserInfo()));
	}
	
	/**
	 * Find tpl excel.
	 * 导出模板到EXCEL
	 * @return the string
	 * @throws Exception the exception
	 */
	public String findTplExcel() throws Exception {
		return defaultExportXLS(TPL_FN, tplCol, tplMngrService.findTplPage(getFilters(),getExcelPageInfo(),getUserInfo()));
	}
	
	/**
	 * Find tpl detail.
	 * 加载模板详细内容
	 * @return the string
	 * @throws Exception the exception
	 */
	public String findTplDetail() throws Exception {
		return resultData(tplMngrService.findTplDetail(getFilters(),getUserInfo(),getParameter(FLD_TPL_ID)));
	}
	
	/**
	 * Preview tpl.
	 * 预览模板
	 * 
	 * @return the string
	 * @throws Exception the exception
	 */
	public String previewTpl() throws Exception {
		MsgTplVo tpl = getValidateBean(MsgTplVo.class);
		Map<String,Object> params = tplMngrService.findTestTplParams(getFilters(), getUserInfo());
		return resultData(StringTemplateViewer.printWithStringTemplate("ID:" + (tpl.getTplId()==null?-1:tpl.getTplId()), tpl.getTplContent(), params));
	}
	
	/**
	 * 发送测试邮件
	 * @return
	 * @throws Exception
	 */
	public String sendTestMail() throws Exception {
		MsgMailVo mailVo = getValidateBean(MsgMailVo.class);
		Map<String,Object> params = tplMngrService.findTestTplParams(getFilters(), getUserInfo());
		mailVo.setSubject("测试邮件");
		mailVo.setContent(StringTemplateViewer.printWithStringTemplate("ID:TEST", mailVo.getContent(), params));
		return resultData(mailService.doSendMail(getUserInfo(), mailVo));
	}
	
	/**
	 * Find tpl params.
	 * 查询模板参数
	 * @return the string
	 * @throws Exception the exception
	 */
	public String findTplParams() throws Exception {
		return resultData(tplMngrService.findTplParams(getFilters(),getUserInfo()));
	}
	
	/**
	 * Insert tpl.
	 * 新增模板
	 * @return the string
	 * @throws Exception the exception
	 */
	public String insertTpl() throws Exception {
		return resultData(tplMngrService.doInsertTpl(getFilters(),getUserInfo(),getValidateBean(MsgTplVo.class)));
	}
	
	/**
	 * Update tpl.
	 * 修改模板
	 * @return the string
	 * @throws Exception the exception
	 */
	public String updateTpl() throws Exception {
		return resultData(tplMngrService.doUpdateTpl(getFilters(),getUserInfo(),getValidateBean(MsgTplVo.class)));
	}

	/**
	 * Set tpl.
	 * 设置模板
	 * @return the string
	 * @throws Exception the exception
	 */
	public String setTpl() throws Exception {
		SysParamVo vo = getValidateBean(SysParamVo.class);
		List<SysParamVo> paramList=new ArrayList<SysParamVo>();
		List<Filter> qfilters= new ArrayList<Filter>();
		qfilters.add(new Filter("paramCode", Filter.STRING, vo.getParamCode(),
				Filter.Comparison.EQ));
		ExtData<SysParamVo> dbvolist=sysParamService.findSysParamList(qfilters, getUserInfo());
		if(dbvolist.getRoot().size()!=0){
		SysParamVo dbvo=dbvolist.getRoot().get(0);
		dbvo.setParamCode(vo.getParamCode());
		dbvo.setParamValue(vo.getParamValue());
		paramList.add(dbvo);
		}
		else{
			throw new BaseException(ErrorCode.CODE_4022,
					ErrorCode.CODE_4022_MSG_P);
		}
		return resultData(sysParamService.doUpdateSysParam(qfilters, getUserInfo(), paramList));
	}
	
	/**
	 * 设置 模板管理Service.
	 *
	 * @param tplMngrService the new 模板管理Service
	 */
	public void setTplMngrService(ITplMngrService tplMngrService) {
		this.tplMngrService = tplMngrService;
	}

	/**
	 * 设置 邮件Service.
	 *
	 * @param mailService the new 邮件Service
	 */
	public void setMailService(IMailService mailService) {
		this.mailService = mailService;
	}
	
	/**
	 * 设置 参数Service.
	 *
	 * @param sysParamService the new 参数Service
	 */
	public void setSysParamService(ISysParamService sysParamService) {
		this.sysParamService = sysParamService;
	}
	
}
