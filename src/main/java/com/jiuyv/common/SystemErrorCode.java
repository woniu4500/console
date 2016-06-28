package com.jiuyv.common;

/**
 * The Interface ErrorCode.
 * 

 * @author 
 * @since 2014-1-3 10:32:29
 * @version 1.0.0
 */
public interface SystemErrorCode {
	
	// 99__:系统\框架错误信息
	/** CODE:9901 */
	String CODE_9901 = "9901";
	/** MSG:页面对象获取失败 */
	String CODE_9901_MSG = "页面对象获取失败";
	/** CODE:9902 */
	String CODE_9902 = "9902";
	/** MSG:数据库插入单条记录失败. */
	String CODE_9902_MSG = "数据库插入单条记录失败";
	/** MSG:数据库更新单条记录失败. */
	String CODE_9902_MSG_1 = "数据库更新单条记录失败";
	/** MSG:数据更新失败，请重新加载页面后继续操作*/
	String CODE_9902_MSG_2 = "数据更新失败，请重新加载页面后继续操作";
	/** CODE:9903 格式转换错误 */
	String CODE_9903 = "9903";
	
	String CODE_2003 = "2003";
	/** MSG:该会员已存在 */
	String CODE_2003_MSG = "该会员已存在";
	
	
	/** CODE:2021. */
	String CODE_2021 = "2021";
	/** MSG:未查询到指定商户. */
	String CODE_2021_MSG = "未查询到指定商户";
	/** CODE:2022. */
	String CODE_2022 = "2022";
	/** MSG:该商户已经是无效状态. */
	String CODE_2022_MSG = "该商户已经是无效状态";
	/** MSG:该商户已经是无效状态,无效状态商户不可更新 */
	String CODE_2022_MSG_1 = "该商户已经是无效状态,无效状态商户不可更新";
	/** CODE:2023 */
	String CODE_2023 = "2023";
	/** MSG:该商户已存在 */
	String CODE_2023_MSG = "该商户已存在";
	
	/** MSG:验证码无效*/
	String CODE_2004_MSG = "验证码无效";
	/** MSG:原始密码错误*/
	String CODE_2005_MSG = "原始密码错误";
	/** MSG:修改密码失败*/
	String CODE_2006_MSG = "修改密码失败";
	/** MSG:原始密码错误*/
	String CODE_2007_MSG = "账号不存在,请注册";
	
	
	
}
