package com.jiuyv.common.env;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class SystemUtil.
 * 系统辅助工具

 * @author 
 * @since 2014-2-14 10:03:14
 * @version 1.0.0
 */
public final class SystemUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SystemUtil.class);
	/** 获取系统属性  */
	private static final Properties SYS_PROP = System.getProperties(); 
	/** 系统运行时 */
	private static final Runtime RUNTIME = Runtime.getRuntime();
	/** 操作系统  */
	public static final String OS_NAME = SYS_PROP.getProperty("os.name");
	/** 操作系统版本 */
	public static final String OS_VERSION = SYS_PROP.getProperty("os.version");
	/** 用户语言 */
	public static final String OS_LANG = SYS_PROP.getProperty("user.language");
	/** 系统架构 */
	public static final String OS_ARCH = SYS_PROP.getProperty("os.arch");
	/** 换行符 */
	public static final String LINE_SEPARATOR = SYS_PROP.getProperty("line.separator");
	/** 文件分隔符 */
	public static final String FILE_SEPARATOR = SYS_PROP.getProperty("file.separator");
	
	/**
	 * Instantiates a new system util.
	 */
	private SystemUtil() {}
	
	/**
	 * Gets the pID.
	 * 获取进程号
	 * @return the pID
	 */
	public static long getPID() {
		String processName = java.lang.management.ManagementFactory
				.getRuntimeMXBean().getName();
		return Long.parseLong(processName.split("@")[0]);
	}
	
	/**
	 * 
	 * 获取本机IP地址
	 * 
	 * @return 本机IP
	 */
	public static String getIPAddress() {
		String ipaddr = "";
		InetAddress ia = null;
		try {
			ia = InetAddress.getLocalHost();
			if (ia == null) {
				LOGGER.error("Unable to get local host. ");
				ipaddr = "some error...";
			} else {
				ipaddr = ia.getHostAddress();
			}
		} catch (UnknownHostException e) {
			LOGGER.error("Error in get host address", e);
		}
		return ipaddr;
	}
	
	/**
	 * 
	 * 获取本机主机名称
	 * 
	 * @return 本机主机名称
	 */
	public static String getHostName() {
		String hostname = "";
		InetAddress ia = null;
		try {
			ia = InetAddress.getLocalHost();
			if (ia == null) {
				LOGGER.error("Unable to get local host. ");
				hostname = "some error...";
			} else {
				hostname = ia.getHostName();
			}
		} catch (UnknownHostException e) {
			LOGGER.error("Error in get host name", e);
		}
		return hostname;
	}

	/**
	 * 获取JVM空闲内存
	 * @return
	 */
	public static long getFreeMemory() {
		return RUNTIME.freeMemory();
	}
	
	/**
	 * 获取JVM总内存
	 * @return
	 */
	public static long getTotalMemory() {
		return RUNTIME.totalMemory();
	}
	
	/**
	 * jvm 内存使用情况
	 * @return
	 */
	public static String demoMemoryUsage() {
		long fm = getFreeMemory();
		long tm = getTotalMemory();
		long um = tm - fm ; 
		return String.format("JVM Memory: %d MB / %d MB, % .2f %%", um/1048576, tm/1048576, (um+0.0)/tm * 100 );
	}
	
}
