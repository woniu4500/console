package com.jiuyv.hhc.console.message.action;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.math.NumberUtils;

import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.common.excel.CellDataRender;
import com.jiuyv.common.excel.CellDataType;
import com.jiuyv.hhc.console.basic.action.DefaultPageSupportAction;
import com.jiuyv.hhc.console.message.service.ISiteMsgService;
import com.jiuyv.hhc.console.security.util.TreeUtil;
import com.jiuyv.hhc.model.message.MsgDict;
import com.jiuyv.hhc.model.message.MsgMessageVo;
import com.jiuyv.hhc.model.message.MsgRecvVo;

/**
 * The Class SiteMsgAction.
 * 站内信Action
 * 

 * @author 
 * @since 2014-2-17 9:54:41
 * @version 1.0.0
 */
public class SiteMsgAction extends DefaultPageSupportAction {
	//--- Constants --------
	private static final String FLD_RECV_ID = "recvId";
	private static final String FLD_MSG_ID = "msgId";
	private static final String FLD_SEND_ID = "sendId";
	private static final String RECV_EXP_FN = "信息收件箱";
	private static final String SEND_EXP_FN = "信息列表";
	/** 接收方信息列  */
	private static List<CellDataType> recvMsgCol = new ArrayList<CellDataType>(); 
	/** 发送方信息列  */
	private static List<CellDataType> sendMsgCol = new ArrayList<CellDataType>(); 
	//--- Feilds 	--------
	/** 站内信Service */
	private ISiteMsgService siteMsgService ; 
	
	//--- Static Init Block	--------
	static {
		sendMsgCol.add(new CellDataType("msgId", "ID"));
		sendMsgCol.add(new CellDataType("msgTitle", "标题"));
		sendMsgCol.add(new CellDataType("sendAcct", "作者"));
		sendMsgCol.add(new CellDataType("statusDesc", "状态"));
		sendMsgCol.add(new CellDataType("showFlagDesc", "显示"));
		sendMsgCol.add(new CellDataType("sendTime", "发送时间", CellDataRender.time));
		sendMsgCol.add(new CellDataType("expireTime", "过期时间", CellDataRender.time));
		
		recvMsgCol.add(new CellDataType("msgId", "ID"));
		recvMsgCol.add(new CellDataType("msgTitle", "标题"));
		recvMsgCol.add(new CellDataType("sendAcct", "作者"));
		recvMsgCol.add(new CellDataType("readFlagDesc", "状态"));
		recvMsgCol.add(new CellDataType("showFlagDesc", "显示"));
		recvMsgCol.add(new CellDataType("sendTime", "发送时间", CellDataRender.time));
		recvMsgCol.add(new CellDataType("expireTime", "过期时间", CellDataRender.time));
	}
	//--- Methods --------
	/**
	 * 
	 * 查询 接收方 信息 (信息简要)
	 * @return
	 */
	public String findRecvMsgPage() throws Exception {
		return resultData(siteMsgService.findMsgPage(getUserFilters(FLD_RECV_ID), getPageInfo(), getUserInfo()));
	}
	
	/**
	 * 查询导出 接收方 信息 (信息简要)
	 * @return
	 */
	public String findRecvMsgExcel() throws Exception {
		return defaultExportXLS(RECV_EXP_FN, recvMsgCol, siteMsgService.findMsgPage(getUserFilters(FLD_RECV_ID), getPageInfo(), getUserInfo()));
	}
	
	/**
	 * 获取用户最近收到的未读消息
	 * @return
	 * @throws Exception
	 */
	public String userRecentMsg() throws Exception {
		return resultData(siteMsgService.findUserTipMsg(getUserFilters(FLD_RECV_ID), getExcelPageInfo(), getUserInfo()));
	}
	/**
	 * 读取信息
	 * @return
	 */
	public String readMsg() throws Exception {
		return resultData(siteMsgService.doReadMsg(getUserFilters(FLD_RECV_ID), getUserInfo(), getParameter(FLD_MSG_ID)));
	}
	
	/**
	 * 标记删除邮件
	 * @return
	 */
	public String recycleMsg() throws Exception {
		return resultData(siteMsgService.doRecycleMsg(getUserFilters(FLD_RECV_ID), getUserInfo(), getParameter(FLD_MSG_ID)));
	}

	/**
	 * 查询发送方 信息 (包括草稿邮件)
	 * @return
	 */
	public String findSendMsgPage() throws Exception {
		return resultData(siteMsgService.findSendMsgPage(getUserFilters(FLD_SEND_ID), getPageInfo(), getUserInfo()));
	}
	/**
	 * 查询导出发送方 信息 (包括草稿邮件)
	 * @return
	 */
	public String findSendMsgExcel() throws Exception {
		return defaultExportXLS(SEND_EXP_FN,sendMsgCol,siteMsgService.findSendMsgPage(getUserFilters(FLD_SEND_ID), getPageInfo(), getUserInfo()));
	}
	
	/**
	 * 查询信息详情
	 * @return
	 */
	public String findMsgDetail() throws Exception {
		return resultData(siteMsgService.findMsgDetail(getUserFilters(FLD_SEND_ID), getUserInfo(), getParameter(FLD_MSG_ID)));
	}
	
	/**
	 * 查询信息的接收人
	 * @return
	 * @throws Exception
	 */
	public String findMsgRecvList() throws Exception {
		return resultData(siteMsgService.findMsgRecvList(getUserFilters(FLD_SEND_ID), getUserInfo(), getParameter(FLD_MSG_ID)));
	}
	
	/**
	 * 保存信息草稿
	 * @return
	 */
	public String saveMsgDraft() throws Exception {
		return resultData(siteMsgService.doSaveMsgDraft(getUserFilters(FLD_SEND_ID), getUserInfo(), getValidateBean(MsgMessageVo.class)));
	}
	
	/**
	 * （保存并）发送信息
	 * @return
	 */
	public String saveSendMsg() throws Exception {
		MsgMessageVo msgBean = getValidateBean(MsgMessageVo.class);
		JSONObject jsonObj = JSONObject.fromObject(getJsonObject());
		List<MsgRecvVo> recvList = convertRecvList(msgBean, jsonObj.getString(FLD_RECV_ID));
		return resultData(siteMsgService.doSendMsg(getUserFilters(FLD_SEND_ID), getUserInfo(), msgBean, recvList));
	}

	/**
	 * 删除信息草稿
	 * @return
	 */
	public String deleteMsgDraft() throws Exception {
		return resultData(siteMsgService.doDeleteMsgDraft(getUserFilters(FLD_SEND_ID), getUserInfo(), getValidateBean(MsgMessageVo.class)));
	}
	
	/**
	 * 获取接收列表
	 * @return
	 * @throws BaseException 
	 */
	private List<MsgRecvVo> convertRecvList(MsgMessageVo msgBean, String recvIds) throws BaseException {
		List<MsgRecvVo> list = new ArrayList<MsgRecvVo>();
		String[] recvBuf = recvIds.split(",");
		Long msgId = msgBean.getMsgId();
		Long sendId = getUserInfo().getUserId();
		if (recvBuf != null) {
			for (int i = 0; i < recvBuf.length; i++) {
				if (!recvBuf[i].equals(TreeUtil.ROOT_NODE)) {
					MsgRecvVo vo = new MsgRecvVo();
					vo.setMsgId(msgId);
					vo.setSendId(sendId);
					vo.setRecvId(NumberUtils.toLong(recvBuf[i]));
					vo.setReadFlag(MsgDict.ReadFlag.UNREAD);
					list.add(vo);
				}
			}
		}
		return list;
	}

	/**
	 * 设置 站内信Service.
	 *
	 * @param siteMsgService the new 站内信Service
	 */
	public void setSiteMsgService(ISiteMsgService siteMsgService) {
		this.siteMsgService = siteMsgService;
	}
	
}
