package com.jiuyv.common.mail;

import java.util.Arrays;

/**
 * The Class MailSenderInfo.
 *

 * @author
 * @since 2014-2-25 13:03:52
 * @version 1.0.0
 */
public class MailSenderInfo {

	/** 发送邮件的服务器的IP. */
	private String mailServerHost;

	/** 发送邮件的服务器的端口. */
	private String mailServerPort = "25";

	/** 邮件发送者的地址. */
	private String fromAddress;

	/** 邮件接收者的地址. */
	private String[] toAddress;

	/** 抄送地址 */
	private String[] ccAddress;
	/** 密抄地址 */
	private String[] bccAddress;

	/** 登陆邮件发送服务器的用户名. */
	private String userName;

	/** 登陆邮件发送服务器的密码. */
	private String password;

	/** 是否需要身份验证. */
	private Boolean validate = true;

	/** 邮件主题. */
	private String subject;

	/** 邮件的文本内容. */
	private String content;

	/** 邮件附件的文件名. */
	private String[] attachFileNames;

	public String getMailServerHost() {
		return mailServerHost;
	}

	public void setMailServerHost(String mailServerHost) {
		this.mailServerHost = mailServerHost;
	}

	public String getMailServerPort() {
		return mailServerPort;
	}

	public void setMailServerPort(String mailServerPort) {
		this.mailServerPort = mailServerPort;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean isValidate() {
		return validate;
	}

	public void setValidate(Boolean validate) {
		this.validate = validate;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String[] getAttachFileNames() {
		return attachFileNames;
	}

	public void setAttachFileNames(String[] attachFileNames) {
		if (null != attachFileNames) {
			this.attachFileNames = Arrays.copyOf(attachFileNames,
					attachFileNames.length);
		} else {
			this.attachFileNames = null;
		}
	}

	public String[] getCcAddress() {
		return ccAddress;
	}

	public void setCcAddress(String[] ccAddress) {
		if (null != ccAddress) {
			this.ccAddress = Arrays.copyOf(ccAddress, ccAddress.length);
		} else {
			this.ccAddress = null;
		}
	}

	public String[] getBccAddress() {
		return bccAddress;
	}

	public void setBccAddress(String[] bccAddress) {
		if (null != bccAddress) {
			this.bccAddress = Arrays.copyOf(bccAddress, bccAddress.length);
		} else {
			this.bccAddress = null;
		}
	}

	public void setToAddress(String[] toAddress) {
		if (null != toAddress) {
			this.toAddress = Arrays.copyOf(toAddress, toAddress.length);
		} else {
			this.toAddress = null;
		}
	}

	public String[] getToAddress() {
		return toAddress;
	}

}
