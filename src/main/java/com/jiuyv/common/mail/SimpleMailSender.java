package com.jiuyv.common.mail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class SimpleMailSender.
 *

 * @author 
 * @since 2014-2-25 16:26:49
 * @version 1.0.0
 */
public final class SimpleMailSender {
	
	/** Logger for this class. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SimpleMailSender.class);

	/**
	 * Instantiates a new simple mail sender.
	 */
	private SimpleMailSender() {
	}

	/**
	 * 以文本格式发送邮件.
	 *
	 * @param mailInfo 待发送的邮件的信息
	 * @return the send result
	 */
	public static SendResult sendTextMail(MailSenderInfo mailInfo) {
		// 判断是否需要身份认证
		SimpleAuthenticator authenticator = null;
		if (mailInfo.isValidate()) {
			// 如果需要身份认证，则创建一个密码验证器
			authenticator = new SimpleAuthenticator(mailInfo.getUserName(),
					mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(getProperties(mailInfo), authenticator);
		SendResult ei = new SendResult();
		try {
			Message mailMessage = new MimeMessage(sendMailSession);
			Address from = new InternetAddress(mailInfo.getFromAddress());
			mailMessage.setFrom(from);
			Address[] to = addressArray(mailInfo.getToAddress(), ei);
			mailMessage.setRecipients(Message.RecipientType.TO, to);
			Address[] cc = addressArray(mailInfo.getCcAddress(), ei);
			mailMessage.setRecipients(Message.RecipientType.CC, cc);
			Address[] bcc = addressArray(mailInfo.getBccAddress(), ei);
			mailMessage.setRecipients(Message.RecipientType.BCC, bcc);
			
			// 设置邮件内容
			mailMessage.setSubject(mailInfo.getSubject());
			mailMessage.setSentDate(new Date());
			String mailContent = mailInfo.getContent();
			mailMessage.setText(mailContent);
			// 发送邮件
			Transport.send(mailMessage);
			ei.setSendTime(new SimpleDateFormat("yyyyMMddHHmmss").format(mailMessage.getSentDate()));
			ei.setSuccess(true);
			return ei;
		} catch (MessagingException ex) {
			LOGGER.error("Error in sending message", ex);
			ei.setSuccess(false);
			ei.addError(ex.getMessage());
		}
		return ei;
	}

	/**
	 * 以HTML格式发送邮件.
	 *
	 * @param mailInfo 待发送的邮件信息
	 * @return the send result
	 */
	public static SendResult sendHtmlMail(MailSenderInfo mailInfo) {
		// 判断是否需要身份认证
		SimpleAuthenticator authenticator = null;
		// 如果需要身份认证，则创建一个密码验证器
		if (mailInfo.isValidate()) {
			authenticator = new SimpleAuthenticator(mailInfo.getUserName(),
					mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(
				getProperties(mailInfo), authenticator);
		SendResult ei = new SendResult();
		try {
			Message mailMessage = new MimeMessage(sendMailSession);
			Address from = new InternetAddress(mailInfo.getFromAddress());
			mailMessage.setFrom(from);
			Address[] to = addressArray(mailInfo.getToAddress(), ei);
			mailMessage.setRecipients(Message.RecipientType.TO, to);
			Address[] cc = addressArray(mailInfo.getCcAddress(), ei);
			mailMessage.setRecipients(Message.RecipientType.CC, cc);
			Address[] bcc = addressArray(mailInfo.getBccAddress(), ei);
			mailMessage.setRecipients(Message.RecipientType.BCC, bcc);

			// 设置邮件内容
			mailMessage.setSubject(mailInfo.getSubject());
			mailMessage.setSentDate(new Date());
			Multipart mainPart = new MimeMultipart();
			BodyPart html = new MimeBodyPart();
			html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
			mainPart.addBodyPart(html);
			mailMessage.setContent(mainPart);
			// 发送邮件
			Transport.send(mailMessage);
			ei.setSendTime(new SimpleDateFormat("yyyyMMddHHmmss").format(mailMessage.getSentDate()));
			ei.setSuccess(true);
			return ei;
		} catch (MessagingException ex) {
			LOGGER.error("Error in sending message", ex);
			ei.setSuccess(false);
			ei.addError(ex.getMessage());
		}
		return ei;
	}

	/**
	 * 获得邮件会话属性.
	 *
	 * @param mailInfo the mail info
	 * @return the properties
	 */
	private static Properties getProperties(MailSenderInfo mailInfo) {
		Properties p = new Properties();
		p.put("mail.smtp.host", mailInfo.getMailServerHost());
		p.put("mail.smtp.port", mailInfo.getMailServerPort());
		p.put("mail.smtp.auth", mailInfo.isValidate() ? "true" : "false");
		return p;
	}

	/**
	 * 获取地址数组.
	 *
	 * @param address the address
	 * @param info the info
	 * @return the address[]
	 */
	private static Address[] addressArray(String[] address, SendResult info) {
		if (address == null || address.length == 0) {
			return null;
		}
		List<Address> addrList = new ArrayList<Address>();
		for (int i = 0; i < address.length; i++) {
			try {
				Address ad = new InternetAddress(address[i]);
				addrList.add(ad);
			} catch (AddressException ae) {
				LOGGER.error("Error in format address", ae);
				info.addError("Mail Address [" + address[i] + "] Invalid. ");
				continue;
			}
		}
		Address[] addArr = new Address[addrList.size()];
		addrList.toArray(addArr);
		return addArr;
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
//		MailSenderInfo msi = new MailSenderInfo();
//		msi.setMailServerHost("smtp.163.com");
//		msi.setMailServerPort("25");
//		msi.setUserName("cowyk@163.com");
//		msi.setPassword("cyk5021519042");
//		msi.setFromAddress("cowyk@163.com");
//		msi.setToAddress(new String[] { "cao_yunke@jiuyv.com", "233129684@qq.com" });
//		msi.setCcAddress(new String[]{"cowyk@163.com"});
//		msi.setSubject("多个收件人邮件");
//		msi.setContent("<h2>这个是测试邮件服务器发送的邮件</h2><hr/><p>这个从应用程序发送的测试邮件</p>");
//		msi.setValidate(true);
		// sendTextMail(msi);
		MailSenderInfo msi = new MailSenderInfo();
		msi.setMailServerHost("smtp.qq.com");
		msi.setMailServerPort("25");
		msi.setUserName("623920040@qq.com");
		msi.setPassword("450052016lihuan");
		msi.setFromAddress("623920040@qq.com");
		msi.setToAddress(new String[] { "woniu4500@aliyun.com"});
		msi.setSubject("多个收件人邮件");
		msi.setContent("<h2>这个是测试邮件服务器发送的邮件</h2><hr/><p>这个从应用程序发送的测试邮件</p>");
		msi.setValidate(true);
		sendHtmlMail(msi);
//		addressArray(msi.getToAddress());
	}
}
