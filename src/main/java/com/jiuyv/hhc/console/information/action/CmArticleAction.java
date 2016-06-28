package com.jiuyv.hhc.console.information.action;

import java.util.ArrayList;
import java.util.List;

import com.jiuyv.common.excel.CellDataType;
import com.jiuyv.hhc.console.basic.action.DefaultPageSupportAction;
import com.jiuyv.hhc.console.information.service.ICmArticleService;
import com.jiuyv.hhc.model.information.CmArticleVo;



/**
 * <h1>The Class CmArticleAction.</h1>
 * <p>Descriptions:</p>
 *

 * @author
 * @since 2014
 * @version 1.0.0
 */
public class CmArticleAction extends DefaultPageSupportAction {

	private ICmArticleService service ;
private static List<CellDataType> columns = new ArrayList<CellDataType>();

	/** Constant String FLD_ORG_CODE: String :. */
	private static final String FLD_ORG_CODE = "orgCode";
	
	static {
		columns.add(new CellDataType("artSeq", "内部序号"));
		columns.add(new CellDataType("version", "VERSION"));
		columns.add(new CellDataType("catSeq", "所属文章"));
		columns.add(new CellDataType("artRemark", "摘要"));
		columns.add(new CellDataType("artContent", "内容"));
		columns.add(new CellDataType("artTitle", "标题"));
		columns.add(new CellDataType("artAuthor", "作者"));
		columns.add(new CellDataType("artPubTime", "发布时间"));
		columns.add(new CellDataType("artStatus", "文章状态"));
		columns.add(new CellDataType("isRecmd", "是否推荐"));
		columns.add(new CellDataType("isTops", "是否置顶"));
		columns.add(new CellDataType("metaWords", "页面关键词"));
		columns.add(new CellDataType("metaDes", "页面描述"));
		columns.add(new CellDataType("artHits", "点击数"));
		columns.add(new CellDataType("artUrl", "静态地址"));
		columns.add(new CellDataType("recCrtTime", "记录创建时间"));
		columns.add(new CellDataType("recCrtAcc", "记录创建用户帐号"));
		columns.add(new CellDataType("lastUptOrg", "最后更新机构"));
		columns.add(new CellDataType("lastUptTime", "最后修改时间"));
		columns.add(new CellDataType("lastUptAcc", "最后修改用户帐号"));
	}
	
	/**
	 * 查询文章信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findCmArticle() throws Exception {
		return resultData(service.find(getOrgFilters(FLD_ORG_CODE), getPageInfo()));
	}
	
	/**
	 * 查询文章信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findCmArticleDetail() throws Exception {
		return resultData(service.findDetail(Long.valueOf(getStringField("artSeq"))));
	}

	/**
	 * 文章查询导出excel.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findCmArticleExcel() throws Exception {
		return defaultExportXLS("文章信息", columns
				, service.find(getOrgFilters(FLD_ORG_CODE), getExcelPageInfo()));
	}


	/**
	 * 新增.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doInsertCmArticle() throws Exception {
		return resultData(service.doInsert(getValidateBean(CmArticleVo.class),getUserDetailInfo()));
	}

	/**
	 * 修改.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doUpdateCmArticle() throws Exception {
		return resultData(service.doUpdate(getValidateBean(CmArticleVo.class),getUserDetailInfo()));
	}
	
	/**
	 * 删除.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doDeleteCmArticle() throws Exception {
		return resultData(service.doDelete(getValidateBean(CmArticleVo.class),getUserDetailInfo()));
	}
	
	/**
	 * 提交.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doCommitCmArticle() throws Exception {
		return resultData(service.doCommit(getValidateBean(CmArticleVo.class),getUserDetailInfo()));
	}
	
	/**
	 * 驳回.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doRejectCmArticle() throws Exception {
		return resultData(service.doReject(getValidateBean(CmArticleVo.class),getUserDetailInfo()));
	}
	
	/**
	 * 发布.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doReleaseCmArticle() throws Exception {
		return resultData(service.doRelease(getValidateBean(CmArticleVo.class),getUserDetailInfo()));
	}
	
	/**
	 * 审核页面跳转.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doCheckPage() throws Exception {
		return SUCCESS;
	}
	/**
	 * @param service the service to set
	 */
	public void setService(ICmArticleService service) {
		this.service = service;
	} 
	
}
