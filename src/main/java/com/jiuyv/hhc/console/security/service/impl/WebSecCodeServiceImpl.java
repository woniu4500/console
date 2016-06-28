package com.jiuyv.hhc.console.security.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jiuyv.common.SystemErrorCode;
import com.jiuyv.common.database.AssistService;
import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.common.database.util.ExtDataUtil;
import com.jiuyv.hhc.console.security.service.IWebSecCodeService;
import com.jiuyv.hhc.model.common.dao.SysParamDao;
import com.jiuyv.hhc.model.customer.WebMemberInfoVo;
import com.jiuyv.hhc.model.customer.dao.WebMemberInfoDao;
import com.jiuyv.hhc.model.security.dao.WebSecCodeDao;
import com.jiuyv.hhc.sms.model.SMSData;
import com.jiuyv.hhc.sms.model.WebSecCodeVo;
import com.jiuyv.hhc.sms.util.SecCodeUtil;


/**
 * The Class WebSecCodeServiceImpl.
 *
 * @company Shanghai JiuYu Software Systems CO.,LTD.
 * @author
 * @since 2014-1-3 15:45:38
 * @version 1.0.0
 */
public class WebSecCodeServiceImpl extends AssistService implements IWebSecCodeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebSecCodeServiceImpl.class);

	private WebSecCodeDao webSecCodeDao;
	
	private SysParamDao sysParamDao;
	
	private WebMemberInfoDao webMemberInfoDao;

	
	public void setWebSecCodeDao(WebSecCodeDao webSecCodeDao) {
		this.webSecCodeDao = webSecCodeDao;
	}

	public void setSysParamDao(SysParamDao sysParamDao) {
		this.sysParamDao = sysParamDao;
	}

	public void setWebMemberInfoDao(WebMemberInfoDao webMemberInfoDao) {
		this.webMemberInfoDao = webMemberInfoDao;
	}



	/**
	 * 发送.
	 *
	 * @param memberVo
	 *            the member vo
	 * @param filters
	 *            the filters
	 * @return the ext data< member vo>
	 * @throws Exception
	 *             the exception
	 */
	public ExtData<WebSecCodeVo> doSendRegVaild(WebSecCodeVo vo) throws Exception {
		WebMemberInfoVo uvo = webMemberInfoDao.findByMobile(vo.getMobileNo());
		if(uvo != null){
			LOGGER.error(uvo.getMobile()+"请求发送注册验证码,但已有此用户");
			throw new BaseException (SystemErrorCode.CODE_2003,SystemErrorCode.CODE_2003_MSG);
		}
		return doSendVaild(vo);
	}
	
	public ExtData<WebSecCodeVo> doSendVaild(WebSecCodeVo vo) throws Exception {
		String apikey = sysParamDao.findByKey("sys.mes.apikey").getParamValue();
		String tplid = sysParamDao.findByKey("sys.mes.vaild.tplid").getParamValue();
		String url = sysParamDao.findByKey("sys.mes.url.utf8").getParamValue();
		String delay = sysParamDao.findByKey("sys.mes.delay").getParamValue();
		String interval = sysParamDao.findByKey("sys.mes.interval").getParamValue();
		Map<String,String> param = new HashMap<String,String>();
		param.put("company", sysParamDao.findByKey("sys.mes.company").getParamValue());
		SecCodeUtil sc = new SecCodeUtil(apikey,tplid,url,delay,interval,param);
		SMSData data=sc.doSend(webSecCodeDao, vo);
		if(data.isSuccess()){
			//vo.setSecCode(data.getVailCode());
			ExtData<WebSecCodeVo> edata = ExtDataUtil.genWithSingleData(vo);
			edata.setSyserr(data.getRespinfo());
			return edata;
		}else{
			LOGGER.error(vo.getMobileNo()+"请求发送注册验证码,发送失败,原因是"+data.getRespinfo());
			return ExtDataUtil.genWithExceptions(data.getRespinfo());
		}
	}
}
