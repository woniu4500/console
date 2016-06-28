package com.jiuyv.common.report;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import net.sf.jasperreports.web.util.WebHtmlResourceHandler;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 这个类用来做jasperReport报表的导出.
 */
@SuppressWarnings({"rawtypes","unchecked"})
public final class ReportUtil {
	/**
	 * Logger for this class
	 */
	private static Logger logger = LoggerFactory.getLogger(ReportUtil.class);

	private static String characterEncoding = "UTF-8";

	private ReportUtil() {
	}

	private static InputStream getIns(String path) {
		return ReportUtil.class.getClassLoader().getResourceAsStream(path);
	}

	/**
	 * 导出报表excel.
	 * 
	 * @param path
	 *            jasper文件路径
	 * @param parameters
	 *            jasper文件参数
	 * @param list
	 *            the 绑定的javabean的数据集合
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public static void reportExcel(String path, Map parameters, List list)
			throws Exception {
		InputStream ins = getIns(path);
		if (ins == null) {
			return;
		}
		JasperPrint jasperPrint = null;
		synchronized (ins) {
			jasperPrint = JasperFillManager.fillReport(ins, parameters,
					new JRBeanCollectionDataSource(list));
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding(characterEncoding);
		response.addHeader("Content-Disposition",
				"attachment; filename=report.xls");
		OutputStream out = response.getOutputStream();
		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
				Boolean.FALSE);
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
		exporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN,
				Boolean.FALSE);
		exporter.exportReport();
		out.flush();
		out.close();
		ins.close();
	}

	/**
	 * 导出报表 pdf.
	 * 
	 * @param path
	 *            jasper文件路径
	 * @param parameters
	 *            jasper文件需要的参数
	 * @param list
	 *            the 绑定的javabean的数据集合
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public static void reportPdf(String path, Map parameters, List list)
			throws Exception {
		InputStream ins = getIns(path);
		if (ins == null) {
			return;
		}
		JasperPrint jasperPrint = null;
		synchronized (ins) {
			jasperPrint = JasperFillManager.fillReport(ins, parameters,
					new JRBeanCollectionDataSource(list));
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/pdf");
		response.setCharacterEncoding(characterEncoding);
		response.addHeader("Content-Disposition",
				"attachment; filename=report.pdf");
		OutputStream out = response.getOutputStream();
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING,
				"UniGB-UCS2-H");
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
		exporter.exportReport();
		out.flush();
		out.close();
		ins.close();
	}

	/**
	 * 导出Word Docx.
	 * 
	 * @param path
	 *            the jasper文件路径
	 * @param parameters
	 *            jasper参数
	 * @param list
	 *            the 绑定的javabean的数据集合
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public static void reportDocx(String path, Map parameters, List list)
			throws Exception {
		InputStream ins = getIns(path);
		if (ins == null) {
			return;
		}
		JasperPrint jasperPrint = null;
		synchronized (ins) {
			jasperPrint = JasperFillManager.fillReport(ins, parameters,
					new JRBeanCollectionDataSource(list));
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/vnd.ms-word");
		response.setCharacterEncoding(characterEncoding);
		response.addHeader("Content-Disposition",
				"attachment; filename=report.docx");
		OutputStream out = response.getOutputStream();
		JRDocxExporter exporter = new JRDocxExporter();
		exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING,
				"UniGB-UCS2-H");
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
		exporter.exportReport();
		out.flush();
		out.close();
		ins.close();
	}

	/**
	 * 导出Word Docx.
	 * 
	 * @param path
	 *            the jasper文件路径
	 * @param parameters
	 *            jasper参数
	 * @param list
	 *            the 绑定的javabean的数据集合
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public static void reportWord(String path, Map parameters, List list)
			throws Exception {
		InputStream ins = getIns(path);
		if (ins == null) {
			return;
		}
		JasperPrint jasperPrint = null;
		synchronized (ins) {
			jasperPrint = JasperFillManager.fillReport(ins, parameters,
					new JRBeanCollectionDataSource(list));
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/rtf");
		response.setCharacterEncoding(characterEncoding);
		response.addHeader("Content-Disposition",
				"attachment; filename=report.rtf");
		OutputStream out = response.getOutputStream();
		JRRtfExporter exporter = new JRRtfExporter();
		exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING,
				"UniGB-UCS2-H");
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
		exporter.exportReport();
		out.flush();
		out.close();
		ins.close();
	}
	/**
	 * 导出HTML.
	 * 
	 * @param path
	 *            the jasper文件路径
	 * @param parameters
	 *            jasper参数
	 * @param list
	 *            the 绑定的javabean的数据集合
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public static void reportHtml(String path, Map parameters, List list)
			throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding(characterEncoding);
		HttpServletRequest request = ServletActionContext.getRequest();
		PrintWriter out = response.getWriter();
		InputStream ins = getIns(path);
		if (ins == null) {
			return;
		}
		JasperPrint jasperPrint = null;
		synchronized (ins) {
			jasperPrint = JasperFillManager.fillReport(ins, parameters, new JRBeanCollectionDataSource(list));
		}
		JRHtmlExporter exporter = new JRHtmlExporter();
		exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML, "<HR>");
		exporter.setParameter(JRHtmlExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
		request.getSession().setAttribute( ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
		exporter.setImageHandler(new WebHtmlResourceHandler("./imageForJasper?image={0}&time=" + System.currentTimeMillis()));
		exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, characterEncoding);
		exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML, "<HR>");
		exporter.setParameter( JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRHtmlExporterParameter.SIZE_UNIT, "pt");
		// 增加参数，导出html时文字过长，自动换行
		exporter.setParameter(JRHtmlExporterParameter.IS_WRAP_BREAK_WORD, Boolean.TRUE);

		exporter.exportReport();
		if (out != null) {
			try {
				out.flush();
				out.close();
			} catch (Exception e) {
				logger.info("", e);
			}
		}
		ins.close();
	}

	/**
	 * 导出html,去掉一些自动去空格的参数
	 * 
	 * @param path
	 *            the path
	 * @param parameters
	 *            the parameters
	 * @param list
	 *            the list
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public static void reportNewHtml(String path, Map parameters, List list)
			throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding(characterEncoding);
		HttpServletRequest request = ServletActionContext.getRequest();
		PrintWriter out = response.getWriter();
		InputStream ins = getIns(path);
		if (ins == null) {
			return;
		}
		JasperPrint jasperPrint = null;
		synchronized (ins) {
			jasperPrint = JasperFillManager.fillReport(ins, parameters, new JRBeanCollectionDataSource(list));
		}
		JRHtmlExporterT exporter = new JRHtmlExporterT();
		request.getSession().setAttribute( ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
		exporter.setImageHandler(new WebHtmlResourceHandler("./imageForJasper?image={0}&time="+System.currentTimeMillis()));
		exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, characterEncoding);
		exporter.setParameter(JRHtmlExporterParameter.SIZE_UNIT, "px");
		// 增加参数，导出html时文字过长，自动换行
		exporter.setParameter(JRHtmlExporterParameter.IS_WRAP_BREAK_WORD, Boolean.TRUE);

		exporter.exportReport();
		if (out != null) {
			try {
				out.flush();
				out.close();
			} catch (Exception e) {
				logger.info("", e);
			}
		}
		ins.close();
	}
}
