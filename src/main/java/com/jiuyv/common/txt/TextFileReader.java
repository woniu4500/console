package com.jiuyv.common.txt;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.jiuyv.common.database.exception.BaseException;

/**
 * 读取File文件
 * 
 * @author guanzh
 * 
 */
public final class TextFileReader {

	// 默认缓冲区大小
	private static final int DEFAULT_BUFFER_LEN = 1024;
	private static final int TEXT_HEAD_LEN = 3;
//	private static final long SKIP_TWO_BYTES=2;
	private static final String ENCODING_FOR_ANSI = "gb2312";
	private static final String ENCODING_FOR_UNICODE = "UTF-16LE";
	private static final String ENCODING_FOR_UNICODE_BIG_ENDIAN = "Unicode";
	private static final String ENCODING_FOR_UTF8 = "UTF-8";
	private static final byte UTF8_HEAD1=-17;
	private static final byte UTF8_HEAD2=-69;
	private static final byte UTF8_HEAD3=-65;
	private static final byte HEAD_BYTE1=-1;
	private static final byte HEAD_BYTE2=-2;

	private TextFileReader(){
		
	}
	/**
	 * 读取文本内容，每行以\n分隔
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String readFileContentAsString(File file) throws Exception {
		return readFileContentAsString(file, null, null, DEFAULT_BUFFER_LEN);
	}

	/**
	 * 读取文本内容，每行以\n分隔
	 * 
	 * @param file
	 * @param encoding
	 *            文件打开的编码方式
	 * @return
	 * @throws Exception
	 */
	public static String readFileContentAsString(File file, String encoding)
			throws Exception {
		return readFileContentAsString(file, encoding, null, DEFAULT_BUFFER_LEN);
	}

	/**
	 * 读取文本内容，每行以\n分隔
	 * 
	 * @param file
	 * @param bufLen
	 *            设置缓冲区大小
	 * @return
	 * @throws Exception
	 */
	public static String readFileContentAsString(File file, int bufLen)
			throws Exception {
		return readFileContentAsString(file, null, null, bufLen);
	}

	/**
	 * 读取文本内容，以特定字符分隔
	 * 
	 * @param file
	 * @param encoding
	 *            文本文件打开的编码方式 例如 GBK,UTF-8
	 * @param sep
	 *            分隔符 例如：#，默认为空格
	 * @return
	 * @throws Exception
	 */
	public static String readFileContentAsString(File file, String encoding,
			String sep) throws Exception {
		return readFileContentAsString(file, encoding, sep, DEFAULT_BUFFER_LEN);
	}

	/**
	 * 读取文本文件内容，以行的形式读取，
	 * 
	 * @param file
	 * @param encoding
	 *            文本文件打开的编码方式 例如 GBK,UTF-8
	 * @param sep
	 *            分隔符 例如：#，默认为\n;
	 * @param bufLen
	 *            设置缓冲区大小
	 * @return
	 * @throws Exception
	 */
	private static String readFileContentAsString(File file, String encoding,
			String sep, int bufLen) throws Exception {
		String sepratorString=null;
		if (file == null || file.length() == 0) {
			return "";
		}
		if (!file.exists()) {
			return "";
		}
		if (sep == null || sep.equals("")) {
			sepratorString = "\n";
		}
		else{
			sepratorString=sep;
		}
		StringBuffer str = new StringBuffer("");
		FileInputStream fs = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			fs = new FileInputStream(file);
			if (encoding == null || encoding.trim().equals("")) {
				byte[] head = new byte[TEXT_HEAD_LEN];
				BufferedInputStream bis=new  BufferedInputStream(fs,bufLen);
				bis.mark(TEXT_HEAD_LEN);
				bis.read(head);
				String type = getTextEncodingType(head);
				//System.out.println(type);
				if (type.equals(ENCODING_FOR_ANSI)) {
					bis.reset();
				}
				if(type.equals(ENCODING_FOR_UNICODE)||type.equals(ENCODING_FOR_UNICODE_BIG_ENDIAN)){
					bis.reset();
					//丢弃两个byte
					bis.read(new byte[2]);
				}
				isr = new InputStreamReader(bis, type);
			} else {
				isr = new InputStreamReader(fs, encoding.trim());
			}
			br = new BufferedReader(isr, bufLen);

			String data = "";
			while ((data = br.readLine()) != null) {
				str.append(data).append(sepratorString);
			}

		} catch (Exception e) {
			throw new BaseException("","read failed",e);
		} finally {
			try {
				if (br != null){
					br.close();
				}
				if (isr != null){
					isr.close();
				}
				if (fs != null){
					fs.close();
				}
			} catch (Exception e) {
				throw new BaseException("","close stream failed",e);
			}

		}
		return str.toString();
	}

	/**
	 * 读取文本内容
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static List<String> readFileContentAsList(File file)
			throws Exception {
		return new ArrayList<String>(readFileContentAsList(file, null,
				DEFAULT_BUFFER_LEN));
	}

	/**
	 * 读取文本内容
	 * 
	 * @param file
	 * @param encoding
	 *            文件打开的编码方式
	 * @return
	 * @throws Exception
	 */
	public static List<String> readFileContentAsList(File file, String encoding)
			throws Exception {
		return new ArrayList<String>(readFileContentAsList(file, encoding,
				DEFAULT_BUFFER_LEN));
	}

	/**
	 * 读取文本内容
	 * 
	 * @param file
	 * @param bufLen
	 *            设置缓冲区大小
	 * @return
	 * @throws Exception
	 */
	public static List<String> readFileContentAsList(File file, int bufLen)
			throws Exception {
		return new ArrayList<String>(readFileContentAsList(file, null, bufLen));
	}

	/**
	 * 读取文本文件内容，以行的形式读取，
	 * 
	 * @param file
	 * @param encoding
	 *            文本文件打开的编码方式 例如 GBK,UTF-8
	 * @param bufLen
	 *            设置缓冲区大小
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static List<String> readFileContentAsList(File file,
			String encoding, int bufLen) throws Exception {
		ArrayList fileContents = new ArrayList<String>();
		if (file == null || file.length() == 0) {
			return fileContents;
		}
		if (!file.exists()) {
			return fileContents;
		}
		FileInputStream fs = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			fs = new FileInputStream(file);
			if (encoding == null || encoding.trim().equals("")) {
				byte[] head = new byte[TEXT_HEAD_LEN];
				BufferedInputStream bis=new  BufferedInputStream(fs,bufLen);
				bis.mark(TEXT_HEAD_LEN);
				bis.read(head);
				String type = getTextEncodingType(head);
				//System.out.println(type);
				if (type.equals(ENCODING_FOR_ANSI)) {
					bis.reset();
				}
				if(type.equals(ENCODING_FOR_UNICODE)||type.equals(ENCODING_FOR_UNICODE_BIG_ENDIAN)){
					bis.reset();
					//丢弃两个byte
					bis.read(new byte[2]);
				}
				isr = new InputStreamReader(bis, type);
			} else {
				isr = new InputStreamReader(fs, encoding.trim());
			}
			br = new BufferedReader(isr, bufLen);

			String data = "";
			while ((data = br.readLine()) != null) {
				if (data.length() != 0) {
					fileContents.add(data);
				}
			}

		} catch (Exception e) {
			throw new BaseException("","read failed",e);
		} finally {
			try {
				if (br != null){
					br.close();
				}
				if (isr != null){
					isr.close();
				}
				if (fs != null){
					fs.close();
				}
			} catch (Exception e) {
				throw new BaseException("","close stream failed",e);
			}

		}
		return fileContents;
	}

	/**
	 * 判断txt文件的编码
	 * 
	 * @param head
	 * @return
	 */
	private static String getTextEncodingType(byte[] head) {
		if (head[0] == HEAD_BYTE1 && head[1] == HEAD_BYTE2){
			return ENCODING_FOR_UNICODE;
		}
		if (head[0] == HEAD_BYTE2 && head[1] == HEAD_BYTE1){
			return ENCODING_FOR_UNICODE_BIG_ENDIAN;
		}
		if (head[0] == UTF8_HEAD1 && head[1] == UTF8_HEAD2 && head[2] == UTF8_HEAD3){
			return ENCODING_FOR_UTF8;
		}
		return ENCODING_FOR_ANSI;
	}

}
