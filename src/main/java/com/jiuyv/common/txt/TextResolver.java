package com.jiuyv.common.txt;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.jiuyv.common.database.exception.BaseException;

/**
 * 解析File文件
 * @author guanzh
 *
 */
public final class TextResolver {
	
	//private static final String ENCODING_GBK="GBK";
	private static final String ERROR_READ_FILE="读取文件失败";
	private static final String ERROR_RESOLVE_FAIL="解析失败";
	
	private TextResolver(){
		
	}
	/**
	 * 解析File文件
	 * @param file
	 * @return
	 */
	public static List<ArrayList<String>> resolveTextFile(File file) throws Exception{
		//设置初始状态
		List<ArrayList<String>> dataList=null;
		List<String> stringList=null;
		//读取文件内容
		try{
			stringList=TextFileReader.readFileContentAsList(file);
		}catch(Exception e){
			throw new BaseException("",ERROR_READ_FILE,e);
		}
		try{
			dataList=resolveTextData(stringList);
		}catch(Exception e){
			throw new BaseException("",ERROR_RESOLVE_FAIL+"{"+e.getMessage()+"}",e);
		}
		return dataList;
	}
	/**
	 * 解析字符串list为实体
	 * @param stringList
	 * @return
	 * @throws Exception
	 */
	private static List<ArrayList<String>> resolveTextData(
			List<String> stringList) throws Exception {
		List<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();
		for (String row : stringList) {
			ArrayList<String> rowData = new ArrayList<String>();
			String[] splitStrings = row.split(" ");
			for (String splitString : splitStrings) {
				splitString = splitString.trim();
				if (splitString.length()!=0) {
					//水平制表符
					String[] ss = splitString.split("\\t");
					if (ss.length == 1) {
						rowData.add(splitString);
					} else {
						for (String s : ss) {
							if (s.trim().length()!=0) {
								rowData.add(s);
							}
						}
					}
				}
			}
			dataList.add(rowData);

		}
		return dataList;
	}
}
