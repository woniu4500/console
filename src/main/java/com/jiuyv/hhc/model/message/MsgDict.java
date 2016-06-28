package com.jiuyv.hhc.model.message;

/**
 * The Interface MsgDict.
 *

 * @author 
 * @since 2014-2-17 15:52:27
 * @version 1.0.0
 */
public interface MsgDict {

	/**
	 * The Interface MsgStatus.
	 * 消息状态
	 * 
	 * 1:草稿,2:已发送,3:已删除
	
	 * @author 
	 * @since 2014-2-17 15:52:27
	 * @version 1.0.0
	 */
	interface MsgStatus {
		/** 1:草稿. */
		String DRAFT = "1";
		/** 2:已发送. */
		String SENDED = "2";
		/** 3:已删除. */
		String DELETE = "3";
	}
	
	/**
	 * The Interface ShowFlag.
	 * 0:提示显示,1:列表显示
	
	 * @author 
	 * @since 2014-2-17 15:52:27
	 * @version 1.0.0
	 */
	interface ShowFlag {
		/** 0:提示显示. */
		String TIP = "0";
		/** 1:列表显示. */
		String LIST = "1";
	}
	
	/**
	 * The Interface ReadFlag.
	 * 0:未读,1:已读,2:删除
	
	 * @author 
	 * @since 2014-2-17 15:52:27
	 * @version 1.0.0
	 */
	interface ReadFlag {
		/** 0:未读. */
		String UNREAD = "0";
		/** 1:已读. */
		String READED = "1";
		/** 2:删除. */
		String DELETE = "2";
	}
	
	/**
	 * 邮件发送状态
	 * 1: 未发送 2: 已发送 9: 发送失败.
	 *
	
	 * @author 
	 * @since 2014-2-26 14:17:46
	 * @version 1.0.0
	 */
	interface MailStatus {
		/** 1: 未发送. */
		String TO_SEND = "1";
		/** 2: 已发送. */
		String SENDED = "2";
		/** 9: 发送失败. */
		String FAILED = "9";
	}
}
